class CreateVersionsFromArchivedCareerMember < ActiveRecord::Migration[7.0]
  def change
    Career::Member.where.not(id: Career::Member.joins(:versions).ids).archived.find_each do |member|
      member.versions.create!(item_type: 'Career::Member', event: 'archived', created_at: member.updated_at)
    end
  end
end
