# frozen_string_literal: true

namespace :resumes do
  desc 'This is task evaluated resume with open ai'
  task evaluate: :environment do
    Resume.failed.find_each do |resume|
      ResumeAutoAnswerService.evaluate_resume(resume)
    end
  end
end
