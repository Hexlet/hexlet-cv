# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/career_member_mailer
class CareerMemberMailerPreview < ActionMailer::Preview
  def new_career_member_email
    user = User.find_by(email: 'full@email.com')
    career_member = Career::Member.find_by(user:)
    CareerMemberMailer.with(career_member:, user:).new_career_member_email
  end

  def career_member_finish_email
    user = User.find_by(email: 'full@email.com')
    career_member = Career::Member.find_by(user:)
    CareerMemberMailer.with(career_member:, user:).career_member_finish_email
  end

  def next_step_open_source_email
    user = User.find_by(email: 'full@email.com')
    career_member = Career::Member.find_by(user:)

    CareerMemberMailer.with(user:, career_member:).next_step_open_source_email
  end
end
