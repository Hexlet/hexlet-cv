# frozen_string_literal: true

module ResumeAnswerMutator
  def self.create(resume, params, current_user)
    answer = resume.answers.build params
    answer.user = current_user
    resume.user.notifications.create!(kind: :new_answer, resource: answer) if answer.save

    answer
  end
end
