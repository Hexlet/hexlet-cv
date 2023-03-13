# frozen_string_literal: true

class VacancyPolicy < ApplicationPolicy
  def edit?
    update?
  end

  def update?
    @user.admin? || !@record.published?
  end
end
