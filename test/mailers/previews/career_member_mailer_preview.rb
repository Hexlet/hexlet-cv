# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/career_member_mailer
class CareerMemberMailerPreview < ActionMailer::Preview
  def new_career_member_email
    career_member = Career::Member.last
    user = career_member.user
    CareerMemberMailer.with(career_member:, user:).new_career_member_email
  end
end
