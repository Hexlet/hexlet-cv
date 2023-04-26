# frozen_string_literal: true

module CareerRepository
  extend ActiveSupport::Concern

  included do
    scope :for_user, ->(user) { joins(members: :user).where(members: { user: }) }
  end
end
