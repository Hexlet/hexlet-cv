# frozen_string_literal: true

class Resume::AnswerPolicy < ApplicationPolicy
  def change_applying_state?
    @record.resume.user == @user
  end

  def edit?
    author?
  end

  def update?
    author?
  end

  def destroy?
    author?
  end
end
