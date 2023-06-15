# frozen_string_literal: true

class NotificationPolicy < ApplicationPolicy
  def update?
    author?
  end
end
