class PopulateApplyingStateInResumeAnswers < ActiveRecord::Migration[6.0]
  def change
    Resume::Answer.find_each do |a|
      a.applying_state = 'pending'
      a.save!
    end
  end
end
