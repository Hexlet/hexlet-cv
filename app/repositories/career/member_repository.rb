# frozen_string_literal: true

module Career::MemberRepository
  extend ActiveSupport::Concern

  included do
    scope :without_archive_members, -> { where.not(state: :archived) }
  end
end
