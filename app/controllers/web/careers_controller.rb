# frozen_string_literal: true

class Web::CareersController < Web::ApplicationController
  before_action :authenticate_user!

  def show
    @career = Career.find_by(slug: params[:id])
    raise ActiveRecord::RecordNotFound if @career.nil?
  end
end
