# frozen_string_literal: true

class Career::Step::MemberPolicy < ApplicationPolicy
  def finish?
    @user && @record.user == @user
  end
end
