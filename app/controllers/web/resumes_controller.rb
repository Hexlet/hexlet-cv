# frozen_string_literal: true

class Web::ResumesController < ApplicationController
  def show
    @resume = Resume.find(params[:id])
    @answer = @resume.answers.build
  end
end
