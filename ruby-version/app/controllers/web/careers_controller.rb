# frozen_string_literal: true

class Web::CareersController < Web::ApplicationController
  before_action :authenticate_user!

  def show
    @career = Career.find_by!(slug: params[:id])
    @career_member = Career::Member.find_by(user: current_user, career: @career)
  end
end
