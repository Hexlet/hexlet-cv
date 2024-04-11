# frozen_string_literal: true

class Web::Account::ProfilesController < Web::Account::ApplicationController
  skip_before_action :require_last_name_and_first_name!

  def edit
    @user = Web::Account::ProfileForm.find(current_user.id)
  end

  def update
    @user = current_user.becomes(Web::Account::ProfileForm)
    if @user.update(profile_params)
      f(:success)
      redirect_to edit_account_profile_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy
    UserService.remove!(current_user)
    sign_out
    f(:success)
    redirect_to root_path
  rescue StandardError
    f(:error)
    redirect_to edit_account_profile_path
  end

  private

  def profile_params
    params.require(:web_account_profile_form)
  end
end
