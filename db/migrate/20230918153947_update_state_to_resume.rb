class UpdateStateToResume < ActiveRecord::Migration[7.0]
  def change
    resume = Resume.find_by(id: 3067)

    return unless resume

    result.mark_as_failed!
  end
end
