class FixResumeCounters < ActiveRecord::Migration[6.0]
  def change
    Resume.find_each { |r| Resume.reset_counters(r.id, :answers) }
  end
end
