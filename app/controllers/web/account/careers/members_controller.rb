# frozen_string_literal: true

class Web::Account::Careers::MembersController < Web::Account::Careers::ApplicationController
  def index
    scope = current_user.career_members.includes(:career)
    @active_members = scope.active
    @finished_members = scope.finished
  end
end
