# frozen_string_literal: true

class Web::Admin::VacanciesController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    respond_to do |format|
      format.html do
        @q = Vacancy.ransack(query)
        @vacancies = @q.result(distinct: true).page(params[:page])
      end

      format.csv do
        q = Vacancy.includes(:creator).ransack(query)
        vacancies = q.result(distinct: true)

        headers = %w[id title state creator company_name created_at published_at]
        send_file_headers!(filename: "vacancies-#{Time.zone.today}.csv")
        self.response_body = generate_csv(vacancies, headers) do |vacancy|
          [
            vacancy.id,
            vacancy.title,
            vacancy.aasm(:state).human_state,
            "#{vacancy.creator.email}(#{vacancy.creator.first_name} #{vacancy.creator.last_name})",
            vacancy.company_name,
            l(vacancy.created_at, format: :long),
            show_date_if(vacancy.published_at, :long)
          ]
        end
      end
    end
  end

  def new
    @vacancy = Web::Admin::VacancyForm.new
  end

  def edit
    vacancy = Vacancy.find params[:id]
    @vacancy = vacancy.becomes(Web::Admin::VacancyForm)
  end

  def create
    @vacancy = Web::Account::VacancyForm.new(params[:vacancy])
    @vacancy.creator = current_user

    if @vacancy.save
      f(:success)
      redirect_to admin_vacancies_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    vacancy = Vacancy.find params[:id]
    @vacancy = vacancy.becomes(Web::Admin::VacancyForm)
    if @vacancy.update(params[:vacancy])
      f(:success)
      redirect_to edit_admin_vacancy_path(@vacancy)
    else
      render :edit, status: :unprocessable_entity
    end
  end
end
