# frozen_string_literal: true

module ResumeRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).published.joins(:user).merge(User.permitted) }
    # has_many :approved_comments, lambda { |resume|
    #   resume.comments.joins(:user).merge(User.permitted)
    # }, class_name: 'Resume::Comment'
  end
end
