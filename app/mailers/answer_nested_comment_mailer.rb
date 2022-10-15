# frozen_string_literal: true

class AnswerNestedCommentMailer < ApplicationMailer
  def new_comment_email
    @comment = params[:comment]
    user = params[:user]

    mail(to: user.email, subject: t('.subject'))
  end
end
