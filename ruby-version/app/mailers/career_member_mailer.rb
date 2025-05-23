# frozen_string_literal: true

class CareerMemberMailer < ApplicationMailer
  def new_career_member_email
    @career_member = params[:career_member]
    @user = params[:user]
    @career = @career_member.career

    mail(to: @user.email)
  end

  def career_member_finish_email
    @career_member = params[:career_member]
    @user = params[:user]
    @career = @career_member.career

    mail(to: @user.email)
  end

  def next_step_open_source_email
    @career_member = params[:career_member]
    @user = params[:user]
    @career = @career_member.career

    mail(to: @user.email)
  end
end
