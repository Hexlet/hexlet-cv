# frozen_string_literal: true

class Resume::AnswerPolicy < ApplicationPolicy
  def update?
    @record.user == @user
  end

  class Scope < Scope
    def resolve
      scope.all
    end
  end
end
