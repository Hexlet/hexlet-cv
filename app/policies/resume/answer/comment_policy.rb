# frozen_string_literal: true

class Resume::Answer::CommentPolicy < ApplicationPolicy
  def edit?
    author?
  end

  def destroy?
    author?
  end

  def update?
    author?
  end
end
