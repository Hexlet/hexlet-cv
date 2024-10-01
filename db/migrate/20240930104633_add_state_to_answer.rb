class AddStateToAnswer < ActiveRecord::Migration[7.1]
  def change
    add_column :resume_answers, :publishing_state, :string, default: 'published'
  end
end
