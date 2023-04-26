# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = User.ransack(query)
    @users = @q.result(distinct: true).page(params[:page])
  end

  def edit
    @user = User.find params[:id]
  end

  def update
    user = User.find params[:id]
    @user = user.becomes(Web::Admin::UserForm)
    if @user.update(params[:user])
      f(:success)
      redirect_to admin_users_path
    else
      render :edit, status: :unprocessable_entity
    end
  end
end
