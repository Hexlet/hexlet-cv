# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  before_action only: %i[index archived finished lost] do
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Career::Member.includes(:career_step_members)
                       .joins(:user, :career)
                       .merge(User.permitted)
                       .merge(Career.with_locale)
                       .ransack(query)

    @career_members = @q.result(distinct: true)
    @careers = Career.with_locale
  end

  def index
    scope = @career_members

    respond_to do |format|
      format.html do
        @users_count = @career_members.size
        @active_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@active_career_members)
        @back_to_page = admin_career_member_users_path(page: params[:page])
      end

      format.csv do
        export_users(scope, file_prefix: 'all_students')
      end
    end
  end

  def archived
    scope = @career_members.includes(:versions).archived

    respond_to do |format|
      format.html do
        @users_count = scope.size
        @archived_career_members = scope.page(params[:page]).per(20)
        @versions_by_member_id = @archived_career_members.each_with_object({}) do |member, acc|
          acc[member.id] = member.versions.with_event(:archive).order(created_at: :asc).last
        end
        @users = prepare_data(@archived_career_members)
        @back_to_page = archived_admin_career_member_users_path(page: params[:page])
      end

      format.csv do
        export_users(scope, file_prefix: 'archived_students')
      end
    end
  end

  def finished
    scope = @career_members.finished

    respond_to do |format|
      format.html do
        @users_count = scope.size
        @finished_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@finished_career_members)
      end

      format.csv do
        export_users(scope, file_prefix: 'finished_students')
      end
    end
  end

  def lost
    scope = @career_members
            .active
            .joins(:career_step_members)
            .merge(Career::Step::Member.active.where(created_at: ..4.weeks.ago))

    respond_to do |format|
      format.html do
        @users_count = scope.size
        @lost_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@lost_career_members)
        @back_to_page = lost_admin_career_member_users_path
      end

      format.csv do
        export_users(scope, file_prefix: 'lost_student')
      end
    end
  end

  def show
    @user = User.find(params[:id])
    @career_members = @user.career_members.includes(:career, :career_step_members)
    @progress = @career_members.each_with_object({}) do |member, acc|
      acc[member.id] = {}
      acc[member.id][:career] = member.career
      acc[member.id][:last_activity_at] = member.career_step_members.active.order(created_at: :asc).last&.created_at
      acc[member.id][:progress] = member.progress_by_finished_steps
      acc[member.id][:current_step] = member.current_item&.career_step&.name
    end
    @back_to_page = admin_career_member_user_path(@user)
  end

  def new
    @career_member = Career::Member.new
    @careers = Career.all
  end

  def create
    career = Career.find(career_member_params[:career_id])
    @career_member = Career::MemberMutator.create(career, career_member_params)
    if @career_member.persisted?
      EventSender.serve!(:new_career_member, @career_member)
      f(:success)
      redirect_to admin_career_member_users_path
    else
      f(:error)
      @careers = Career.all
      render :new
    end
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id, :career_id)
  end

  def prepare_data(career_members)
    career_members.each_with_object({}) do |member, acc|
      members = member.career_step_members
      last_finished_or_active_step = members.active.order(created_at: :asc).last || members.finished.order(created_at: :asc).last
      acc[member.id] = {}
      acc[member.id][:user] = member.user
      acc[member.id][:careers] = member.user.careers
      acc[member.id][:current_step] = member.current_item&.career_step&.name
      acc[member.id][:progress] = member.progress_by_finished_steps
      acc[member.id][:last_finished_or_active_step] = last_finished_or_active_step&.created_at
    end
  end

  def export_users(scope, file_prefix: '')
    headers = %i[full_name email career_name start_date finished_date current_step progress last_step_finished_or_active_at state]
    send_file_headers!(filename: "#{file_prefix}-#{Time.zone.today}.csv")
    self.response_body = generate_csv(scope, headers) do |member|
      [
        member.user.full_name,
        member.user.email,
        member.career.name,
        member.created_at,
        member.finished_at,
        member.current_item&.career_step&.name,
        member.progress_by_finished_steps,
        member.career_step_members.where(state: :finished).or(member.career_step_members.where(state: :active)).order(created_at: :asc).last&.created_at,
        member.aasm(:state).human_state
      ]
    end
  end
end
