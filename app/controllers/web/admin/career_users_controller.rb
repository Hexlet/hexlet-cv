# frozen_string_literal: true

class Web::Admin::CareerUsersController < Web::Admin::ApplicationController
  def index
    query = { s: 'id desc' }.merge(params.permit![:q] || {})
    @q = User.permitted.with_careers.ransack(query)
    @users = @q.result.page(params[:page]).per(10)
    @users_with_active_career = @users.merge(Career::Member.active)
    @users_with_archived_career = @users.merge(Career::Member.archived)
    @users_with_finished_career = @users.merge(Career::Member.finished)
  end

  def show
    @user = User.find(params[:id])
    @user_career_members = @user.career_members.includes(:career)
    @back_to_page = admin_career_user_path(@user)
  end
end
