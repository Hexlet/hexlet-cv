# frozen_string_literal: true

module Resume::AnswerRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { joins(:resume, :user).merge(Resume.with_locale.published).merge(User.permitted) }
    # has_many :approved_comments, lambda { |answer|
    #   answer.comments.joins(:user).merge(User.permitted)
    # }, class_name: 'Resume::Answer::Comment'
  end
end
