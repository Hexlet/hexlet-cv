class UpdateStateToResumeUser < ActiveRecord::Migration[7.0]
  def change
    resume = Resume.find_by(id: 3068)

    return unless resume

    resume.mark_as_failed!
  end
end
