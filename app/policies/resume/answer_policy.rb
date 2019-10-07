# frozen_string_literal: true

class Resume::AnswerPolicy < ApplicationPolicy
  def update?
    author?
  end

  def destroy?
    author?
  end
end
