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
    @active_career_members = @career_members
                             .active
                             .page(params[:page])
                             .per(20)

    @users = prepare_data(@active_career_members)
    @back_to_page = admin_career_member_users_path(page: params[:page])
  end

  def archived
    @archived_career_members = @career_members
                               .archived
                               .page(params[:page])
                               .per(20)

    @users = prepare_data(@archived_career_members)
    @back_to_page = archived_admin_career_member_users_path(page: params[:page])
  end

  def finished
    @finished_career_members = @career_members
                               .finished
                               .page(params[:page])
                               .per(20)

    @users = prepare_data(@finished_career_members)
  end

  def lost
    @lost_career_members = @career_members
                           .active
                           .joins(:career_step_members)
                           .includes(:career_step_members)
                           .merge(Career::Step::Member.active.where(created_at: ..2.weeks.ago))
                           .page(params[:page])
                           .per(20)

    @users = prepare_data(@lost_career_members)
    @back_to_page = lost_admin_career_member_users_path
  end

  def show
    @user = User.find(params[:id])
    @career_members = @user.career_members.includes(:career, :career_step_members)
    @progress = @career_members.each_with_object({}) do |member, acc|
      acc[member.id] = {}
      acc[member.id][:career] = member.career
      acc[member.id][:last_activity_at] = member.career_step_members.active.last&.created_at
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
end
