# frozen_string_literal: true

class Web::Resumes::AnswerForm < Resume::Answer
  include ActiveFormModel

  permit :content
end
