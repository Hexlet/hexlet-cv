# frozen_string_literal: true

class Web::UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    @user_resume_answers = @user.resume_answers
    @user_resume_answers_likes_count = @user.resume_answers.sum('likes_count')
    @user_resumes = @user.resumes.web
    @user_resume_comments = @user.resume_comments

    title @user

    set_meta_tags canonical: user_url(@user)
    set_meta_tags og: {
      title: meta_tag_title(@user),
      type: 'website',
      url: user_url(@user)
    }
  end
end
