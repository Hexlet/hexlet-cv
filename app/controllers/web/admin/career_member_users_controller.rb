# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  before_action only: %i[index archived finished lost] do
    query = { s: 'id desc' }.merge(params.permit![:q] || {})
    @q = User.permitted.includes(:careers, :career_members).ransack(query)
    @users = @q.result
    @careers = Career.all
  end

  def index
    @users_with_active_career = @users
                                .only_active_career_members
                                .page(params[:page])
                                .per(20)
  end

  def archived
    @users_with_archived_career = @users
                                  .only_archived_career_members
                                  .page(params[:page])
                                  .per(20)
  end

  def finished
    @users_with_finished_career = @users
                                  .only_finished_career_members
                                  .page(params[:page])
                                  .per(20)
  end

  def lost
    @users_lost_career = @users
                         .with_career_step_members
                         .includes(career_members: :career_step_members)
                         .merge(Career::Step::Member.active.where(created_at: ..1.week.ago))
                         .page(params[:page])
                         .per(20)
  end

  def show
    @user = User.find(params[:id])
    @user_career_members = @user.career_members.includes(:career, :career_step_members)
    @last_ativity_by_id = @user_career_members.each_with_object({}) { |member, acc| acc[member.id] = member.career_step_members.active.last&.created_at }
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
end
