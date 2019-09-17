# frozen_string_literal: true

class Web::UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    @user_answers = @user.resume_answers
    @user_likes = @user.resume_answers.sum('likes_count')
    @user_resumes = @user.resumes.web
  end
end
