# frozen_string_literal: true

module LocaleConcern
  extend ActiveSupport::Concern

  included do
    around_action :switch_locale
  end

  def switch_locale(&)
    locale = extract_locale   
    
    if params[:locale].present?
      I18n.with_locale(params[:locale], &)
    else
      I18n.with_locale(I18n.default_locale, &)
    end    
  end

  def extract_locale
    locale = params[:locale]
    return locale if I18n.available_locales.map(&:to_s).include?(locale)
    nil
  end  
end
