# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  before_action only: %i[index archived finished] do
    query = { s: 'id desc' }.merge(params.permit![:q] || {})
    @q = User.permitted.with_career_members.includes(:careers, :career_members).ransack(query)
    @users = @q.result
    @careers = Career.all
  end

  def index
    @users_with_active_career = @users.merge(Career::Member.active).page(params[:page]).per(20)
  end

  def archived
    @users_with_archived_career = @users.merge(Career::Member.archived).page(params[:page]).per(20)
  end

  def finished
    @users_with_finished_career = @users.merge(Career::Member.finished).page(params[:page]).per(20)
  end

  def show
    @user = User.find(params[:id])
    @user_career_members = @user.career_members.includes(:career)
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
