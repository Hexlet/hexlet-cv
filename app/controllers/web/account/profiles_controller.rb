# frozen_string_literal: true

class Web::Account::ProfilesController < Web::Account::ApplicationController
  def edit; end

  def update
    if current_user.update(profile_params)
      redirect_to edit_account_profile_path
      f(:success)
    else
      render :show
    end
  end

  private

  def profile_params
    params.require(:user).permit(:first_name, :last_name)
  end
end
