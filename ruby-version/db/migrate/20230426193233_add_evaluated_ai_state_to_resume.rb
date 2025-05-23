class AddEvaluatedAiStateToResume < ActiveRecord::Migration[7.0]
  def change
    add_column :resumes, :evaluated_ai_state, :string
  end
end
