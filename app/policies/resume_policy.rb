# frozen_string_literal: true

class ResumePolicy < ApplicationPolicy
  def show?
    @record.published? || author?
  end

  def update?
    author?
  end

  def update_state?
    @user.admin? && !author? && !@record.new_record?
  end
end
