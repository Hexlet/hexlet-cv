# frozen_string_literal: true

module Auth::User
  def banned?
    if current_user.present? && current_user.banned? # rubocop:disable Style/GuardClause
      sign_out current_user
      flash[:error] = 'You have been banned'
      redirect_to root_path
    end
  end
end
