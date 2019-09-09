# frozen_string_literal: true

class ResumePolicy < ApplicationPolicy
  def show?
    @record.published? || @record.user == @user
  end

  class Scope < Scope
    def resolve
      scope.all
    end
  end
end
