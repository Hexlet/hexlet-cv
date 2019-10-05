# frozen_string_literal: true

class Web::HomeController < ApplicationController
  def index
    @resumes = Resume.all
    @resumes = @resumes.without_answers if params[:scope] == 'without_answers'
    @resumes = @resumes.newest if params[:scope] == 'newest'
    @resumes = @resumes.popular if params[:scope] == 'popular'
    @resumes = @resumes.web.page(params[:page])
  end
end
