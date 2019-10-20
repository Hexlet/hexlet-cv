# frozen_string_literal: true

class ResumeAnswerMailer < ApplicationMailer
  def new_answer_email
    @answer = params[:answer]
    @user_url = user_url(@answer.user)
    @answer_url = resume_url(@answer.resume, anchor: "answer-#{@answer.id}")
    @settings_url = account_profile_url(@answer.resume.user)
    mail(to: @answer.resume.user.email, subject: default_i18n_subject)
  end
end
