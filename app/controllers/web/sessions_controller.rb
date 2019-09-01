# frozen_string_literal: true

class Web::SessionsController < ApplicationController
  def new; end

  def create
    user = User.find_by!(email: params[:user][:email])
    sign_in(user)
    redirect_to root_path
  end

  def destroy
    sign_out
    redirect_to root_path
  end
end
