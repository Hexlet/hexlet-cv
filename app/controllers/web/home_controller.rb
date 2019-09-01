# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    @resumes = Resume.page(params[:page])
  end
end
