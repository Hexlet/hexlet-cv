# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  before_action only: %i[index archived finished lost] do
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Career::Member.joins(:user, :career)
                       .merge(User.permitted)
                       .merge(Career.with_locale)
                       .ransack(query)

    @career_members = @q.result
    @careers = Career.all
  end

  def index
    scope = @career_members.active

    respond_to do |format|
      format.html do
        @active_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@active_career_members)
        @back_to_page = admin_career_member_users_path(page: params[:page])
      end

      format.csv do
        export_users(scope, file_prefix: 'all_students-')
      end
    end
  end

  def archived
    scope = @career_members.archived

    respond_to do |format|
      format.html do
        @archived_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@archived_career_members)
        @back_to_page = archived_admin_career_member_users_path(page: params[:page])
      end

      format.csv do
        export_users(scope, file_prefix: 'archived_students-')
      end
    end
  end

  def finished
    scope = @career_members.finished
    respond_to do |format|
      format.html do
        @finished_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@finished_career_members)
      end

      format.csv do
        export_users(scope, file_prefix: 'finished_students-')
      end
    end
  end

  def lost
    scope = @career_members
            .active
            .joins(:career_step_members)
            .includes(:career_step_members)
            .merge(Career::Step::Member.active.where(created_at: ..4.weeks.ago))

    respond_to do |format|
      format.html do
        @lost_career_members = scope.page(params[:page]).per(20)
        @users = prepare_data(@lost_career_members)
        @back_to_page = lost_admin_career_member_users_path
      end

      format.csv do
        export_users(scope, file_prefix: 'lost_student-')
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
      EmailSender.send_new_career_member_email(@career_member)
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
      acc[member.id] = {}
      acc[member.id][:last_name] = member.user.last_name
      acc[member.id][:first_name] = member.user.first_name
      acc[member.id][:email] = member.user.email
      acc[member.id][:careers] = member.user.careers
      acc[member.id][:current_step] = member.current_item&.career_step&.name
      acc[member.id][:progress] = member.progress_by_finished_steps
    end
  end

  def export_users(scope, file_prefix: '')
    headers = %i[full_name email career_name current_step progress last_step_finished_at]
    send_file_headers!(filename: "#{file_prefix}-#{Time.zone.today}.csv")
    self.response_body = generate_csv(scope, headers) do |member|
      [
        member.user.full_name,
        member.user.email,
        member.career.name,
        member.current_item&.career_step&.name,
        member.progress_by_finished_steps,
        member.career_step_members.active.order(created_at: :asc).last&.created_at
      ]
    end
  end
end
