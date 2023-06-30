# frozen_string_literal: true

class Web::Admin::CareerUsersController < Web::Admin::ApplicationController
  before_action only: %i[index archived finished] do
    query = { s: 'id desc' }.merge(params.permit![:q] || {})
    @q = User.permitted.with_careers.includes(:careers, :career_members).ransack(query)
    @users = @q.result
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
    @back_to_page = admin_career_user_path(@user)
  end
end
