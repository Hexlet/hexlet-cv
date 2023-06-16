# frozen_string_literal: true

class Web::OmniauthCallbacksController < Devise::OmniauthCallbacksController
  include LocaleConcern

  def github
    @user = User.from_omniauth(request.env['omniauth.auth'])
    I18n.locale = session[:locale] || I18n.default_locale

    if @user.persisted?
      @user.locale = I18n.locale
      @user.save!
      sign_in_and_redirect @user
      set_flash_message(:notice, :success, kind: 'Github') if is_navigational_format?
    else
      session['devise.github_data'] = request.env['omniauth.auth']
      redirect_to new_user_registration_url
    end
  end

  def failure
    redirect_to root_path
  end
end
