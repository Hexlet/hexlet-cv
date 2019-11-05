# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    q = Resume.web.ransack(params[:q])
    @resumes = q.result(distinct: true).page(params[:page])
  end
end
