# frozen_string_literal: true

class Web::Account::ApplicationController < Web::ApplicationController
  before_action :authenticate_user!

  rescue_from Pundit::NotAuthorizedError, with: :can_not_edit_vacancy

  private

  def can_not_edit_vacancy(exception)
    policy_name = exception.policy.class.to_s.underscore
    flash[:alert] = t "#{policy_name}.#{exception.query}", locale: I18n.locale, scope: 'pundit', default: :default

    redirect_to root_path
  end
end
