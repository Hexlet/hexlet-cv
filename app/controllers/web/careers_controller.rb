# frozen_string_literal: true

class Web::CareersController < Web::ApplicationController
  before_action :authenticate_user!

  def index
    @user_career_members = current_user.career_members.includes(:career).without_archive_members
  end
end
