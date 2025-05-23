# frozen_string_literal: true

class ResumeCommentMailer < ApplicationMailer
  def new_comment_email
    @comment = params[:comment]
    mail(to: @comment.resume.user.email, subject: t('.subject'))
  end
end
