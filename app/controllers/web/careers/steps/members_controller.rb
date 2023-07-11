# frozen_string_literal: true

class Web::Careers::Steps::MembersController < Web::Careers::Steps::ApplicationController
  after_action :verify_authorized, only: %i[finish]

  def finish
    career_step_member = Career::Step::Member.find(params[:id])
    authorize career_step_member
    career_member = career_step_member.career_member
    Career::Step::MemberMutator.create!(career_step_member, career_member)
    EmailSender.send_career_member_finish(career_member)
    f(:success)
    redirect_to career_member_path(resource_career, career_member)
  end
end
