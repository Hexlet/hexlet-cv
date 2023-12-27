# frozen_string_literal: true

module Auth
  def banned?
    if current_user_or_guest.banned? # rubocop:disable Style/GuardClause
      sign_out current_user
      flash[:error] = t('.banned')
      redirect_to root_path
    end
  end

  def guest_user
    Guest.new
  end

  def current_user_or_guest
    current_user || guest_user
  end

  def admin_signed_in?
    current_user_or_guest.admin?
  end

  def authenticate_admin!
    return if admin_signed_in?

    flash[:error] = t('forbidden')
    redirect_to root_path
  end

  def require_last_name_and_first_name!
    return unless current_user_or_guest.anonimus?

    redirect_to edit_account_profile_path
  end
end
