# frozen_string_literal: true

# frozen_string_litearl: true

class AiBotHelper
  def self.ai_bot_user
    @ai_bot_user ||= User.find_by(email: ENV.fetch('EMAIL_SPECIAL_USER'))
  end
end
