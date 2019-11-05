# frozen_string_literal: true

require 'test_helper'

class Web::Hooks::SparkpostsControllerTest < ActionDispatch::IntegrationTest
  test '#create bounce' do
    @user = users(:one)
    event = {
      msys: {
        message_event: {
          type: 'bounce',
          rcpt_to: @user.email
        }
      }
    }
    post sparkpost_path(event)

    @user.reload

    assert { @user.bounced_email }
  end

  test '#create spam_complaint' do
    @user = users(:one)
    event = {
      msys: {
        message_event: {
          type: 'spam_complaint',
          rcpt_to: @user.email
        }
      }
    }
    post sparkpost_path(event)

    @user.reload

    assert { @user.marked_as_spam }
  end

  test '#create unknown' do
    @user = users(:one)
    event = {
      msys: {
        message_event: {
          type: 'unknown',
          rcpt_to: @user.email
        }
      }
    }
    post sparkpost_path(event)

    user = users(:one)

    assert { @user == user }
  end
end
