# frozen_string_literal: true

class Web::Admin::Careers::StepsController < Web::Admin::Careers::ApplicationController
  def show
    @step = Career::Step.find(params[:id])
  end

  def new
    @step = Career::Step.new
  end

  def edit
    @step = Career::Step.find(params[:id])
  end

  def create
    form = Web::Admin::Career::StepForm.new(career_step_params)
    @step = Career::StepMutator.create(resource_career, form.attributes)
    if @step.persisted?
      f(:success)
      redirect_to admin_career_path(resource_career)
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @step = Career::Step.find(params[:id])
    step = @step.becomes(Web::Admin::Career::StepForm)

    if step.update(career_step_params)
      f(:success)
      redirect_to admin_career_path(resource_career)
    else
      @step = step.becomes(Web::Admin::Career::StepForm)
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  private

  def career_step_params
    params.require(:career_step)
  end
end
