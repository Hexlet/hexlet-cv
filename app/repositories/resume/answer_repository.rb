# frozen_string_literal: true

module Resume::AnswerRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { joins(:resume).merge(Resume.published).order(id: :desc) }
  end
end
