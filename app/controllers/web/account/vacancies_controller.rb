# frozen_string_literal: true

class Web::Account::VacanciesController < Web::Account::ApplicationController
  after_action :verify_authorized, only: %i[edit update]

  def index
    @vacancies = current_user.vacancies.with_locale
  end

  def new
    @vacancy = Web::Account::VacancyForm.new
  end

  def edit
    vacancy = current_user.vacancies.find params[:id]
    @vacancy = vacancy.becomes(Web::Account::VacancyForm)
    authorize vacancy
  end

  def create
    @vacancy = Web::Account::VacancyForm.new(params[:vacancy])
    @vacancy.creator = current_user
    @vacancy.locale = current_user.locale

    if @vacancy.save
      f(:success)
      redirect_to account_vacancies_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @vacancy = current_user.vacancies.find params[:id]
    authorize @vacancy
    vacancy = @vacancy.becomes(Web::Account::VacancyForm)
    if vacancy.update(params[:vacancy])
      change_visibility(@vacancy)
      f(:success)
      redirect_to account_vacancies_path
    else
      @vacancy = vacancy.becomes(Vacancy)
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy; end

  private

  def change_visibility(vacancy)
    vacancy.archive! if params[:archive]
  end
end
