# frozen_string_literal: true

class Web::Account::VacanciesController < Web::Account::ApplicationController
  def index
    @vacancies = current_user.vacancies
  end

  def new
    @vacancy = Vacancy.new
  end

  def create
    @vacancy = Web::Account::VacancyForm.new(params[:vacancy])
    @vacancy.creator = current_user

    if @vacancy.save
      # change_visibility(@vacancy)
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    @vacancy = current_user.vacancies.find params[:id]
    vacancy = @vacancy.becomes(Web::Account::VacancyForm)
    if vacancy.update(params[:vacancy])
      # change_visibility(@vacancy)
      f(:success)
      redirect_to action: :index
    else
      @vacancy = vacancy.becomes(Vacancy)
      render :edit
    end
  end

  def edit
    @vacancy = current_user.vacancies.find params[:id]
  end

  def destroy; end

  # private

  # def change_visibility(resume)
  #   resume.publish! if params[:publish]
  #   resume.hide! if params[:hide]
  # end
end
