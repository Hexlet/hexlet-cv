class Web::Account::ResumesController < ApplicationController
  def index
    @resumes = current_user.resumes
  end

  def new
    @resume = Resume.new
  end

  def create
    resume_params = params.require(:resume).permit(:link)
    @resume = current_user.resumes.build resume_params
    if @resume.save
      redirect_to action: :index
    else
      render :new
    end
  end

  def destroy
  end
end
