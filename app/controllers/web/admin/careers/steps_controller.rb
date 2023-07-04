# frozen_string_literal: true

class Web::Admin::Careers::StepsController < Web::Admin::Careers::ApplicationController
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
    @step = Web::Admin::Career::StepForm.new(params[:career_step])
    if @step.save
      f(:success)
      redirect_to admin_step_path(@step)
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @step = Career::Step.find(params[:id])
    step = @step.becomes(Web::Admin::Career::StepForm)

    if step.update(params[:career_step])
      f(:success)
      redirect_to admin_step_path(@step)
    else
      @step = step.becomes(Web::Admin::Career::StepForm)
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end
end
