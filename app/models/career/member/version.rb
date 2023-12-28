# frozen_string_literal: true

class Career::Member::Version < PaperTrail::Version
  extend Enumerize

  self.table_name = 'career_member_versions'

  enumerize :event, in: %i[activate archive finish], scope: true, predicates: true

  validates :item, :event, :item_type, presence: true

  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at]
  end
end
