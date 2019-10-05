class AddAnswerCounterCacheToResumes < ActiveRecord::Migration[6.0]
  def up
    add_column :resumes, :answers_count, :integer, default: 0
    Resume.find_each do |resume|
      Resume.reset_counters resume.id, :answers
    end
  end

  def down
    remove_column :resumes, :answers_count
  end
end
