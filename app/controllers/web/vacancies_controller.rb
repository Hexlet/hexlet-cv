# frozen_string_literal: true

class Web::VacanciesController < Web::ApplicationController
  def index
    @vacancies = Vacancy.web.page(params[:page])

    respond_to do |format|
      format.rss
      format.html
    end
  end

  def show
    @vacancy = Vacancy.find(params[:id])
    respond_with @vacancy, location: resume_url(@vacancy)
  end
end
