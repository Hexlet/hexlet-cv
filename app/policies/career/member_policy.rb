# frozen_string_literal: true

class Career::MemberPolicy < ApplicationPolicy
  def show?
    @user && (@record.user == @user || @user.admin?)
  end

  def can_show_step_body?(step_item)
    return true if @user.admin?

    @record.can_show_step_body?(step_item)
  end
end
