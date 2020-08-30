# frozen_string_literal: true

class Web::Account::NewslettersController < Web::Account::ApplicationController
  def edit; end

  def update
    user = current_user.becomes(Web::Account::NewsletterForm)
    if user.update(newsletters_params)
      f(:success)
      redirect_to edit_account_newsletters_path
    else
      f(:error)
      render :edit
    end
  end

  def newsletters_params
    params.require(:user)
  end
end
