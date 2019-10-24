# frozen_string_literal: true

module ResumeRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).published }
  end
end
