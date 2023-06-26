# frozen_string_literal: true

module Career::MemberPresenter
  def progress_percent
    steps_count = career.steps.count
    finished_steps_count = career_step_members.finished.count
    finished_steps_count * 100 / steps_count
  end
end
