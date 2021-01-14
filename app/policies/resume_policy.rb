# frozen_string_literal: true

class ResumePolicy < ApplicationPolicy
  def show?
    @record.published? || author?
  end

  def update?
    author?
  end

  def download?
    author?
  end
end
