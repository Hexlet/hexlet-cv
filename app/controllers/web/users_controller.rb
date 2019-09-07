# frozen_string_literal: true

class Web::UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
  end
end
