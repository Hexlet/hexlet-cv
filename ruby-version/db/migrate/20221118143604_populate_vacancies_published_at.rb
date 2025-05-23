class PopulateVacanciesPublishedAt < ActiveRecord::Migration[6.1]
  def change
    Vacancy.where(published_at: nil, state: [:published, :archived]).update_all('published_at = created_at')
  end
end
