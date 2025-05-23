# frozen_string_literal: true

module Resume::CommentRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc) }
  end
end
