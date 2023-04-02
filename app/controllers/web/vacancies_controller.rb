# frozen_string_literal: true

class Web::VacanciesController < Web::ApplicationController
  def index
    q = Vacancy.web.ransack(params[:q])
    @vacancies = q.result(distinct: true).page(params[:page])
    @vacancy_search_form = Web::Vacancies::SearchForm.new
    @tags = Vacancy.directions_tags
    @page = params[:page]

    respond_to do |format|
      format.html
      format.rss
    end
  end

  def show
    @vacancy = Vacancy.web.find(params[:id])
    respond_with @vacancy, location: resume_url(@vacancy)
  end
end
