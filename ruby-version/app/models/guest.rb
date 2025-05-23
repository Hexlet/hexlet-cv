# frozen_string_literal: true

class Guest
  def id; end

  def guest?
    true
  end

  def admin?
    false
  end

  def banned?
    false
  end

  def anonimus?
    false
  end
end
