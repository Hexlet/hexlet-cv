# frozen_string_literal: true

class Web::Account::NotificationsController < Web::Account::ApplicationController
  def index
    @notifications = current_user.notifications
  end

  def read_all
    @notifications = current_user.notifications.unread

    ActiveRecord::Base.transaction do
      @notifications.each(&:mark_as_read!)
    end
    f(:success)
    redirect_to account_notifications_path
  end
end
