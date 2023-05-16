# frozen_string_literal: true

class Web::Careers::Steps::MembersController < Web::Careers::Steps::ApplicationController
  after_action :verify_authorized, only: %i[finish]

  def finish
    career_step_member = Career::Step::Member.find(params[:id])
    authorize career_step_member
    ActiveRecord::Base.transaction do
      career_step_member.finish!
      @career_member = resource_career.members.where(user: current_user).active.last
      @career_member.finish! if @career_member.may_finish?
    end
    redirect_to career_member_path(resource_career, @career_member)
  end
end
