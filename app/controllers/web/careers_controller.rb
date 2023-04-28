# frozen_string_literal: true

class Web::CareersController < Web::ApplicationController
  before_action :authenticate_user!

  def index
    @user_careers = current_user.careers.web
    user_career_members = current_user.career_members.with_not_archived_member
    @user_career_members_by_career = user_career_members.index_by(&:career_id)
  end

  def show
    @career = Career.find(params[:id])
    @career_member = current_user.career_members.with_not_archived_member.last
  end
end
