class AddEvaluatedOpenAiToResumes < ActiveRecord::Migration[7.0]
  def change
    add_column :resumes, :evaluated_ai, :boolean
  end
end
