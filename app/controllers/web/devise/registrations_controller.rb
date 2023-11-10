# frozen_string_literal: true

class Web::Devise::RegistrationsController < Devise::RegistrationsController
  # rubocop:disable Rails/LexicallyScopedActionFilter
  prepend_before_action :check_captcha, only: [:create]
  # rubocop:enable Rails/LexicallyScopedActionFilter
  include GonInit
  include LocaleConcern

  protected

  def build_resource(hash = {})
    self.resource = Web::UserRegistrationForm.new_with_session(hash, session)
  end

  private

  def check_captcha
    I18n.locale = session[:locale]
    return if verify_recaptcha

    self.resource = resource_class.new sign_up_params
    resource.validate
    resource.errors.add(:base, t('recaptcha.errors.verification_failed', locale: I18n.locale)) unless resource.errors.any?
    set_minimum_password_length
    respond_with_navigational(resource) do
      f(:recaptcha_error, discard: true)
      render :new
    end
  end
end
