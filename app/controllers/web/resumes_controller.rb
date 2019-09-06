# frozen_string_literal: true

class Web::ResumesController < ApplicationController
  def show
    # FIXME: check if user already rated answers
    @resume = Resume.find(params[:id])
    @answer = Resume::Answer.new resume: @resume
    @current_user_answer = @resume.answers.find_by(user: current_user)
  end
end
