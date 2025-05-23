# frozen_string_literal: true

class PopulateNotifications < ActiveRecord::Migration[6.0]
  def change
    Notification.destroy_all

    Resume::Answer.find_each do |answer|
      answer.resume.user.notifications.create!(kind: :new_answer, resource: answer)
    end

    Resume::Comment.find_each do |comment|
      comment.resume.user.notifications.create!(kind: :new_comment, resource: comment)
    end

    Resume::Answer::Comment.find_each do |comment|
      comment.answer.user.notifications.create!(kind: :new_answer_comment, resource: comment)
    end

    Resume::Answer::Like.find_each do |like|
      like.answer.user.notifications.create!(kind: :new_answer_like, resource: like)
    end
  end
end
