# frozen_string_literal: true

module Auth::User
  def banned?
    if current_or_guest_user.banned? # rubocop:disable Style/GuardClause
      sign_out current_user
      flash[:error] = t('.banned')
      redirect_to root_path
    end
  end
end
