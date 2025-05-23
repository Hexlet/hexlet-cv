# frozen_string_literal: true

class Web::Resumes::CommentForm < Resume::Comment
  include ActiveFormModel

  fields :content
end
