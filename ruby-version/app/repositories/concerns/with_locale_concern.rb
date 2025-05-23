# frozen_string_literal: true

module WithLocaleConcern
  extend ActiveSupport::Concern

  included do
    scope :with_locale, ->(locale = I18n.locale) { where(locale:) }
  end
end
