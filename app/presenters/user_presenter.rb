# frozen_string_literal: true

module UserPresenter
  def full_name
    return 'Anonymous' if !first_name? || !last_name?

    "#{first_name} #{last_name}"
  end

  alias to_s full_name
end
