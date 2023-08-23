# frozen_string_literal: true

class RenameTagsVacancy < ActiveRecord::Migration[7.0]
  def change
    return unless Rails.env.production?
    tags = ActsAsTaggableOn::Tag
          .where('EXISTS (?)', ActsAsTaggableOn::Tagging.where(taggable_type: Vacancy.to_s).where('tag_id=tags.id').limit(1).select('1'))
          .where('name ~* ?', '^.+\.(js|php|html)$')

    tags.find_each do |tag|
      new_name = name.tr('.', '-')
      tag.name = new_name
      tag.save!
    end
  end
end
