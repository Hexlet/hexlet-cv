# frozen_string_literal: true

class Web::Account::VacanciesController < Web::Account::ApplicationController
  def index
    @vacancies = current_user.vacancies
  end

  def new
    @vacancy = Web::Account::VacancyForm.new
  end

  def create
    @vacancy = Web::Account::VacancyForm.new(params[:vacancy])
    @vacancy.creator = current_user

    if @vacancy.save
      f(:success)
      redirect_to action: :index
    else
      f(:error)
      render :new
    end
  end

  def update
    @vacancy = current_user.vacancies.find params[:id]
    vacancy = @vacancy.becomes(Web::Account::VacancyForm)
    if vacancy.update(params[:vacancy])
      change_visibility(@vacancy)
      f(:success)
      redirect_to action: :index
    else
      @vacancy = vacancy.becomes(Vacancy)
      f(:error)
      render :edit
    end
  end

  def edit
    vacancy = current_user.vacancies.find params[:id]
    @vacancy = vacancy.becomes(Web::Account::VacancyForm)
  end

  def destroy; end

  private

  def change_visibility(vacancy)
    vacancy.archive! if params[:archive]
  end
end
