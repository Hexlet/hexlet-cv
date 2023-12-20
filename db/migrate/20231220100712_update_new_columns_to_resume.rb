class UpdateNewColumnsToResume < ActiveRecord::Migration[7.0]
  # rubocop:disable Rails/SkipsModelValidations
  def change
    Resume.update_all('about_myself = about_my_self')
    Resume.update_all('projects_description = project_descriptions')
  end
  # rubocop:enable Rails/SkipsModelValidations
end
