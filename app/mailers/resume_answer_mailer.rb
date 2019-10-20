# frozen_string_literal: true

class ResumeAnswerMailer < ApplicationMailer
  def new_answer_email
    @answer = params[:answer]
    mail(to: @answer.resume.user.email)
  end
end
