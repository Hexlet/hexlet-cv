# frozen_string_literal: true

class Web::Resumes::AnswersController < Web::Resumes::ApplicationController
  def show
    @answer = resource_resume.answers.find params[:id]
  end

  def change_applying_state
    @answer = resource_resume.answers.find params[:id]
    authorize @answer
    @answer.aasm(:applying).fire! params[:event]&.to_sym || :apply # FIXME: remove apply after fixing link
    @answer.user.notifications.create!(kind: :answer_applied, resource: @answer)
    f(:success)
    redirect_to resume_path(resource_resume)
  end

  def edit
    @answer = resource_resume.answers.find params[:id]
    authorize @answer
  end

  def create
    form = Web::Resumes::AnswerForm.new(resume_answer_params)
    @answer = Resume::AnswerMutator.create(resource_resume, form.attributes, current_user)
    if @answer.persisted?
      # TODO: Переделать на нормальную реализацию
      EmailSender.send_new_answer_mail(@answer)

      f(:success)
      redirect_to resume_path(resource_resume)
    else
      render :new, status: :unprocessable_entity
    end
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
      render :edit, status: :unprocessable_entity
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
