class AddApplyingStateToResumeAnswer < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_answers, :applying_state, :string
  end
end
