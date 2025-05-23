# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    @q = User.ransack(query_params)
    @users = @q.result(distinct: true).page(params[:page])
  end

  def edit
    @user = Web::Admin::UserForm.find(params[:id])
  end

  def update
    user = User.find params[:id]
    @user = user.becomes(Web::Admin::UserForm)
    if @user.update(params[:web_admin_user_form])
      f(:success)
      redirect_to edit_admin_user_path(@user)
    else
      render :edit, status: :unprocessable_entity
    end
  end

  private

  def query_params
    { s: 'created_at desc' }.merge(params.permit![:q] || {})
  end
end
