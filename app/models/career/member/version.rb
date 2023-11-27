# frozen_string_literal: true

class Career::Member::Version < PaperTrail::Version
  self.table_name = 'career_member_versions'

  validates :item, :event, :item_type, presence: true
end
