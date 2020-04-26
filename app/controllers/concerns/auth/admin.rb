# frozen_string_literal: true

module Auth::Admin
  def admin_signed_in?
    current_user.admin?
  end

  def authenticate_admin!
    return redirect_to root_path unless user_signed_in?
    return redirect_to root_path unless admin_signed_in?
  end
end
