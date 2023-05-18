# frozen_string_literal: true

class Web::CareersController < Web::ApplicationController
  before_action :authenticate_user!

  def show
    @career = Career.find(params[:id])
  end
end
