# frozen_string_literal: true

module ResumeAnswerMutator
  def self.create(resource_resume, params, current_user)
    answer = resource_resume.answers.build params
    answer.user = current_user
    resource_resume.user.notifications.create!(kind: :new_answer, resource: answer) if answer.save

    answer
  end
end
