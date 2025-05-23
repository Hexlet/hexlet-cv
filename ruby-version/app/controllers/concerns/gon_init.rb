# frozen_string_literal: true

module GonInit
  def self.included(base)
    base.before_action do
      gon.google_analytics_key = Rails.application.config.vars[:ga]
    end
  end
end
