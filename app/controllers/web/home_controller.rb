# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    q = Resume.ransack(params[:q])
    @resumes = q.result(distinct: true).web.page(params[:page])
  end
end
