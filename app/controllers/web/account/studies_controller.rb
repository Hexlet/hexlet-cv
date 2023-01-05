# frozen_string_literal: true

class Web::Account::StudiesController < Web::Account::ApplicationController
  def edit
    @profile = current_user.becomes(Web::Account::StudyForm)
  end

  def update
    @profile = current_user.becomes(Web::Account::StudyForm)
    if @profile.update(study_params)
      f(:success)
      redirect_to edit_account_study_path
    else
      f(:error)
      render :edit
    end
  end

  private

  def study_params
    params.require(:profile)
  end
end
