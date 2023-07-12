class UpdateSlugToCareerStep < ActiveRecord::Migration[7.0]
  def change
    Career::Step.find_each do |step|
      step.generate_slug!
      step.save!
    end
  end
end
