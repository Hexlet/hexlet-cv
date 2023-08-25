# frozen_string_literal: true

class Web::Admin::CareersController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Career.ransack(query)
    @careers = @q.result
  end

  def show
    @career = Career.find(params[:id])
    @career_steps = @career.steps.ordered.distinct
    @users = @career.users.distinct
  end

  def new
    @career = Career.new
  end

  def edit
    career = Career.find(params[:id])
    @career = career.becomes(Web::Admin::CareerForm)
    @items = @career.items
    @steps = Career::Step.all
  end

  def create
    @career = Web::Admin::CareerForm.new(params[:career])
    if @career.save
      f(:success)
      redirect_to admin_careers_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    career = Career.find(params[:id])
    @career = career.becomes(Web::Admin::CareerForm)
    if @career.update(params[:career])
      f(:success)
      redirect_to admin_careers_path
    else
      @steps = Career::Step.all
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end
end
