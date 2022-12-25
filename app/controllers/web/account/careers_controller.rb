# frozen_string_literal: true

class Web::Account::CareersController < Web::Account::ApplicationController
  def edit
    @profile = current_user.becomes Web::Account::CareerForm
  end

  def update
    user = current_user.becomes(Web::Account::CareerForm)
    if user.update(career_params)
      f(:success)
      redirect_to edit_account_career_path
    else
      f(:error)
      render :edit
    end
  end

  private

  def career_params
    params.require(:profile)
  end
end
