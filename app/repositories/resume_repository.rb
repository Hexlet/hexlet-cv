# frozen_string_literal: true

module ResumeRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).published }
    scope :newest, -> { where('created_at >= ?', Time.zone.now - 1.day) }
    scope :most_viewed_by_last_day, -> { joins(:impressions).where('impressions.created_at >= ?', Time.zone.now - 1.day) }
    scope :most_commented_by_last_day, -> { joins(:comments).where('resume_comments.created_at >= ?', Time.zone.now - 1.day) }
    scope :most_answered_by_last_day, -> { joins(:answers).where('resume_answers.created_at >= ?', Time.zone.now - 1.day) }
    scope :popular, -> { where(id: most_viewed_by_last_day | most_commented_by_last_day | most_answered_by_last_day) }
    scope :without_answers, -> { where.not(id: Resume::Answer.distinct.pluck(:resume_id)) }
  end

  class_methods do
    def scope_eq(scope)
      return all unless respond_to?(scope)

      public_send(scope)
    end
  end
end
