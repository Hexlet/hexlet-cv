# frozen_string_literal: true

class Web::Resumes::AnswersController < Web::Resumes::ApplicationController
  def edit
    @answer = resource_resume.answers.find params[:id]
    authorize @answer
  end

  def change_applying_state
    @answer = resource_resume.answers.find params[:id]
    authorize @answer
    @answer.aasm(:applying).fire! params[:event] || :apply # FIXME: remove apply after fixing link
    @answer.user.notifications.create!(kind: :answer_applied, resource: @answer)
    f(:success)
    redirect_to resume_path(resource_resume)
  end

  def update
    @answer = resource_resume.answers.find params[:id]
    authorize @answer
    answer = @answer.becomes(Web::Resumes::AnswerForm)
    if answer.update(resume_answer_params)
      f(:success)
      redirect_to resume_path(resource_resume)
    else
      @answer = answer.becomes(Resume::Answer)
      render :edit
    end
  end

  def create
    form = Web::Resumes::AnswerForm.new(resume_answer_params)
    @answer = Resume::AnswerMutator.create(resource_resume, form.attributes, current_user)
    if @answer.persisted?
      f(:success)
      redirect_to resume_path(resource_resume)
      send_new_answer_mail(@answer)
    else
      render :new
    end
  end

  def destroy
    resume = Resume.find params[:resume_id]
    # TODO: add pundit, switch to resume, check current user
    answer = current_user.resume_answers.find(params[:id])
    answer.destroy

    f(:success)
    redirect_to resume_path(resume)
  end

  def resume_answer_params
    params.require(:resume_answer)
  end
end
