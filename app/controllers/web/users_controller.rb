# frozen_string_literal: true

class Web::UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    @user_answers = Resume::Answer.where(user: @user)
    @user_likes = @user_answers.reduce(0) { |sum, answer| sum + answer.likes.count }
    @user_resumes = Resume.web.where(user: @user)
  end
end
