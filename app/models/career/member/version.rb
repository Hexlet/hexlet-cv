# frozen_string_literal: true

# == Schema Information
#
# Table name: career_member_versions
#
#  id         :integer          not null, primary key
#  event      :string           not null
#  item_type  :string           not null
#  whodunnit  :string
#  created_at :datetime
#  item_id    :bigint           not null
#
# Indexes
#
#  index_career_member_versions_on_item_type_and_item_id  (item_type,item_id)
#
class Career::Member::Version < PaperTrail::Version
  extend Enumerize

  self.table_name = 'career_member_versions'

  enumerize :event, in: %i[activate archive finish], scope: true, predicates: true

  validates :item, :event, :item_type, presence: true
end
