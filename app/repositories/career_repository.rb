# frozen_string_literal: true

module CareerRepository
  extend ActiveSupport::Concern

  included do
    scope :without_archive_members, -> { where.not(career_members: { state: :archived }) }
    scope :for_user, ->(user) { joins(members: :user).where(members: { user: }) }
  end
end
