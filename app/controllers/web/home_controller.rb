# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    q = Resume.ransack({scope_eq: params[:q]})
    @resumes = q.result.web.page(params[:page])
  end
end
