# frozen_string_literal: true

class OpenAiJob < ApplicationJob
  queue_as :default

  retry_on StandardError, wait: 5.seconds, attempts: 3

  def perform(resume_id)
    resume = Resume.find(resume_id)
    ResumeAutoAnswerService.evaluate_resume(resume)
  end
end
