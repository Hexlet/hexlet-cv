# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    @resumes = Resume.order(id: :desc).page(params[:page])
  end
end
