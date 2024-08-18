# frozen_string_literal: true

class Web::Admin::VacanciesController < Web::Admin::ApplicationController
  def index
    query = query_params({ s: 'created_at desc' })
    respond_to do |format|
      format.html do
        @go_to = admin_vacancies_path(page: params[:page])
        @q = Vacancy.with_locale.ransack(query)
        @vacancies = @q.result(distinct: true).page(params[:page])
      end

      format.csv do
        q = Vacancy.with_locale.includes(:creator).ransack(query)
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
    @vacancy = resource_vacancy.becomes(Web::Admin::VacancyForm)
  end

  def new_cancelation
    @go_to = params[:go_to]
    @vacancy = resource_vacancy.becomes(Web::Admin::VacancyForm)
  end

  def create
    @vacancy = Web::Admin::VacancyForm.new(params[:vacancy])
    @vacancy.creator = current_user

    if @vacancy.save
      f(:success)
      redirect_to edit_admin_vacancy_path(@vacancy)
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @vacancy = resource_vacancy.becomes(Web::Admin::VacancyForm)
    if Admin::VacancyMutator.update!(@vacancy, params.permit![:vacancy])
      f(:success)
      redirect_to params[:go_to] || edit_admin_vacancy_path(@vacancy)
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def on_moderate
    query = query_params({ s: 'created_at asc' })
    @go_to = on_moderate_admin_vacancies_path(page: params[:page])
    @q = Vacancy.with_locale.on_moderate.ransack(query)
    @vacancies = @q.result(distinct: true).page(params[:page])
  end

  def archive
    resource_vacancy.archive!
    f(:success)
    redirect_to params[:go_to] || admin_vacancies_path(page: params[:page])
  end

  def restore
    resource_vacancy.restore!
    f(:success)
    redirect_to params[:go_to] || admin_vacancies_path(page: params[:page])
  end

  def cancel
    @vacancy = resource_vacancy.becomes(Web::Admin::VacancyForm)

    canceled = Admin::VacancyMutator.cancel!(@vacancy, params.permit![:vacancy])

    if canceled
      f(:success)
      redirect_to params[:go_to] || new_cancelation_admin_vacancy_path(@vacancy)
    else
      f(:error)
      render :new_cancelation, status: :unprocessable_entity
    end
  end

  private

  def query_params(default_params = {})
    default_params.merge(params.permit![:q] || {})
  end

  def resource_vacancy
    @resource_vacancy ||= Vacancy.find params[:id]
  end
end
