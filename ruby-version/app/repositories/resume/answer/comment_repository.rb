# frozen_string_literal: true

module Resume::Answer::CommentRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { joins(:user).merge(User.permitted).order(id: :asc) }
  end
end
