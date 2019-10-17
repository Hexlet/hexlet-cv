# frozen_string_literal: true

module Resume::AnswerRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { where(resume_id: Resume.where(state: 'published')).order(id: :desc) }
  end
end
