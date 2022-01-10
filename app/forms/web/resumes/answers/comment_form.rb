# frozen_string_literal: true

class Web::Resumes::Answers::CommentForm < Resume::Answer::Comment
  include ActiveFormModel

  fields :content
end
