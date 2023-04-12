# frozen_string_literal: true

module Auth
  def banned?
    if current_or_guest_user.banned? # rubocop:disable Style/GuardClause
      sign_out current_user
      flash[:error] = t('.banned')
      redirect_to root_path
    end
  end

  def guest_user
    Guest.new
  end

  def current_or_guest_user
    current_user || guest_user
  end

  def admin_signed_in?
    current_or_guest_user.admin?
  end

  def authenticate_admin!
    return if admin_signed_in?

    flash[:error] = t('forbidden')
    redirect_to root_path
  end
end
