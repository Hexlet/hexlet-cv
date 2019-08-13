class Web::ResumesController < ApplicationController
  def show
    @resume = Resume.find(params[:id])
  end
end
