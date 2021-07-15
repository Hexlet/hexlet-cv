# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = User.ransack(query)
    @users = @q.result(distinct: true).page(params[:page])
  end

  def update
    user = User.find params[:id]
    @user = user.becomes(Web::Admin::UserForm)
    if @user.update(params[:user])
      f(:success)
      redirect_to action: :index
    else
      render :edit
    end
  end

  def edit
    @user = User.find params[:id]
  end
end
