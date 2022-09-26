# frozen_string_literal: true

class Web::Account::ProfilesController < Web::Account::ApplicationController
  skip_before_action :verify_authenticity_token, only: [:check_box]

  def show; end

  def edit; end

  def update
    user = current_user.becomes(Web::Account::ProfileForm)
    if user.update(profile_params)
      f(:success)
      redirect_to account_profile_path
    else
      f(:error)
      render :edit
    end
  end

  def check_box
    current_user.update(profile_params)
  end

  private

  def profile_params
    params.require(:user).permit(profile_checkboxes: [])
  end
end
