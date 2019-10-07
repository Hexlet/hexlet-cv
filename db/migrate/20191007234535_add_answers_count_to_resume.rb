class AddAnswersCountToResume < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :answers_count, :integer, default: 0, null: false
  end
end
