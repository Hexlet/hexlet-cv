# frozen_string_literal: true

class Web::Account::NotificationsController < Web::Account::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = current_user.notifications.includes(:resource).ransack(query)
    @notifications = @q.result.page(params[:page])
  end

  def update
    notification = Notification.find(params[:id])
    authorize notification
    notification.mark_as_read!
    f(:success)
    redirect_to account_notifications_path
  end
end
