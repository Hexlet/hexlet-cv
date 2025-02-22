# frozen_string_literal: true

class Web::Account::VacanciesController < Web::Account::ApplicationController
  after_action :verify_authorized, only: %i[edit update]

  def index
    @q = current_user.vacancies.with_locale.ransack(params[:q])
    @vacancies = @q.result(distinct: true).order(created_at: :desc)
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

    change_visibility(@vacancy)
    if @vacancy.save
      f(:success)
      redirect_to account_vacancies_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    vacancy = current_user.vacancies.find params[:id]
    authorize vacancy
    @vacancy = vacancy.becomes(Web::Account::VacancyForm)
    change_visibility(@vacancy)
    if @vacancy.update(params[:vacancy])
      f(:success)
      redirect_to account_vacancies_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy; end

  private

  def change_visibility(vacancy)
    if params[:on_moderate]
      vacancy.send_to_moderate
    else
      vacancy.send_to_draft
    end
  end
end
