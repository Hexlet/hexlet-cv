class FixUserCounters < ActiveRecord::Migration[6.0]
  def change
    Resume::Answer::Like.counter_culture_fix_counts
  end
end
