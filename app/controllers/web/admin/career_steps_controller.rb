# frozen_string_literal: true

class Web::Admin::CareerStepsController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Career::Step.ransack(query)
    @steps = @q.result.page(params[:page])
  end

  def show
    @step = Career::Step.find(params[:id])
    @careers = @step.careers
  end

  def new
    @step = Career::Step.new
  end

  def edit
    @step = Career::Step.find(params[:id])
    @careers = @step.careers
  end

  def create
    @step = Web::Admin::Career::StepForm.new(career_step_params)
    if @step.save
      f(:success)
      redirect_to admin_career_step_path(@step)
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
      redirect_to admin_career_step_path(@step)
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
