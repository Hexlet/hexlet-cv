# frozen_string_literal: true

module Career::MemberPresenter
  def progress_percent
    steps_count = career.steps.count
    finished_steps_count = career_step_members.where(career_step: career.steps).finished.count
    finished_steps_count * 100 / steps_count
  end

  def progress_by_finished_steps
    I18n.t('career_progress', finished_steps: finished_steps_count, total_steps: steps_count)
  end
end
