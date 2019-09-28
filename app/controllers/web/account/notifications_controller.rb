# frozen_string_literal: true

class Web::Account::NotificationsController < Web::Account::ApplicationController
  def index
    @notifications = current_user.notifications
  end
end
