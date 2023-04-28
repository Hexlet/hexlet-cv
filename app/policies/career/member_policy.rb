# frozen_string_literal: true

class Career::MemberPolicy < ApplicationPolicy
  def show?
    @user && @record.user == @user
  end
end
