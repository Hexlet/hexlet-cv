# frozen_string_literal: true

module Auth::Admin
  def admin_signed_in?
    current_or_guest_user.admin?
  end

  def authenticate_admin!
    return redirect_to root_path unless admin_signed_in?
  end
end
