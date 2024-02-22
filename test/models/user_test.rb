# frozen_string_literal: true

# == Schema Information
#
# Table name: users
#
#  id                        :integer          not null, primary key
#  about                     :string
#  bounced_email             :boolean
#  confirmation_sent_at      :datetime
#  confirmation_token        :string
#  confirmed_at              :datetime
#  current_sign_in_at        :datetime
#  current_sign_in_ip        :string
#  email                     :string
#  email_disabled_delivery   :boolean
#  encrypted_password        :string           default(""), not null
#  failed_attempts           :integer          default(0), not null
#  first_name                :string
#  last_name                 :string
#  last_sign_in_at           :datetime
#  last_sign_in_ip           :string
#  locale                    :string
#  locked_at                 :datetime
#  marked_as_spam            :boolean
#  provider                  :string
#  remember_created_at       :datetime
#  reset_password_sent_at    :datetime
#  reset_password_token      :string
#  resume_answer_likes_count :integer          default(0), not null
#  resume_mail_enabled       :boolean
#  role                      :string
#  sign_in_count             :integer          default(0), not null
#  state                     :string
#  uid                       :string
#  unconfirmed_email         :string
#  unlock_token              :string
#  created_at                :datetime         not null
#  updated_at                :datetime         not null
#
# Indexes
#
#  index_users_on_confirmation_token    (confirmation_token) UNIQUE
#  index_users_on_email                 (email) UNIQUE
#  index_users_on_reset_password_token  (reset_password_token) UNIQUE
#  index_users_on_unlock_token          (unlock_token) UNIQUE
#
require 'test_helper'

class UserTest < ActiveSupport::TestCase
  test 'new user with default attribute resume_mail_enabled' do
    attrs = FactoryBot.attributes_for :user

    user = User.new(attrs)
    user.save!

    assert { user.valid? }
    assert { user.resume_mail_enabled }
  end
end
