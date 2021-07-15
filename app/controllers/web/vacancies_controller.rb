# frozen_string_literal: true

class Web::VacanciesController < Web::ApplicationController
  def index
    @vacancies = Vacancy.web.page(params[:page])
  end

  def show
    @vacancy = Vacancy.find(params[:id])
  end
end
