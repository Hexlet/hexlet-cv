# frozen_string_literal: true

module UserRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).permitted }
    scope :web_admin, -> { order(id: :desc) }
  end
end
