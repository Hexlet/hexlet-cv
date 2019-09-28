# frozen_string_literal: true

module ResumeAnswerMutator
  def self.create(resource_resume, params, current_user)
    answer = resource_resume.answers.build params
    answer.user = current_user
    if answer.save
      resource_resume.user.notifications.create!(kind: :new_answer, resource: answer)
    end

    answer
  end
end
