# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    @resumes = Resume.web.page(params[:page])
  end
end
