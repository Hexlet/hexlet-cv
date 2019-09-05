# frozen_string_literal: true

class Web::ResumesController < ApplicationController
  def show
    @resume = Resume.find(params[:id])
    @answer = Resume::Answer.new resume: @resume
  end
end
