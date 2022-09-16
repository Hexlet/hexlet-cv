# frozen_string_literal: true

class AnswerCommentMailer < ApplicationMailer
  def new_comment_email
    @comment = params[:comment]

    mail(to: @comment.answer.user.email, subject: t('.subject'))
  end
end
