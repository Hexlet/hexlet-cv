# frozen_string_literal: true

module Career::MemberRepository
  extend ActiveSupport::Concern

  included do
    scope :with_not_archived, -> { where.not(state: :archived) }
  end
end
