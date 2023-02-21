# frozen_string_literal: true

class Web::Admin::VacanciesController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Vacancy.ransack(query)
    @vacancies = @q.result(distinct: true).page(params[:page])
  end

  def edit
    vacancy = Vacancy.find params[:id]
    @vacancy = vacancy.becomes(Web::Admin::VacancyForm)
  end

  def update
    vacancy = Vacancy.find params[:id]
    @vacancy = vacancy.becomes(Web::Admin::VacancyForm)
    if @vacancy.update(params[:vacancy])
      f(:success)
      redirect_to admin_vacancies_path
    else
      render :edit, status: :unprocessable_entity
    end
  end
end
