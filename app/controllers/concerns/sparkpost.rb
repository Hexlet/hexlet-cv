# frozen_string_literal: true

module Sparkpost
  def sparkpost_types
    %w[bounce spam_complaint]
  end

  def handle_bounce(event)
    email = event[:msys][:message_event][:rcpt_to]
    user = User.find_by(email: email)
    user.bounced_email = true
    user.save!
  end

  def handle_spam_complaint(event)
    email = event[:msys][:message_event][:rcpt_to]
    user = User.find_by(email: email)
    user.marked_as_spam = true
    user.save!
  end
end
