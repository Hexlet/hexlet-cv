# frozen_string_literal: true

class Web::Admin::StepsController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Step.ransack(query)
    @steps = @q.result
  end

  def show
    @step = Step.find(params[:id])
  end

  def new
    @step = Step.new
  end

  def edit
    @step = Step.find(params[:id])
  end

  def create
    @step = Web::Admin::StepForm.new(params[:step])
    if @step.save
      f(:success)
      redirect_to admin_steps_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @step = Step.find(params[:id])
    step = @step.becomes(Web::Admin::StepForm)

    if step.update(params[:step])
      f(:success)
      redirect_to admin_steps_path
    else
      @step = step.becomes(Web::Admin::StepForm)
      render :edit, status: :unprocessable_entity
    end
  end
end
