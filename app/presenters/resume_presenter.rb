# frozen_string_literal: true

module ResumePresenter
  def skills
    skills_description&.split("\n") || []
  end
end
