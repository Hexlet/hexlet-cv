# frozen_string_literal: true

module ResumeRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).published }
    scope :without_answers, -> { where(answers_count: [0, nil]) }
    scope :newest, -> { where('created_at >= ?', Time.now - 1.day) }
    scope :popular, -> { order(impressions_count: :desc, id: :desc) }
  end
end
