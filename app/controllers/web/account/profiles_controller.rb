# frozen_string_literal: true

class Web::Account::ProfilesController < Web::Account::ApplicationController
  def show; end

  def update
    if current_user.update(profile_params)
      render :show
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
