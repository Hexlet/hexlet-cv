# frozen_string_literal: true

class N8nClientStub
  def self.send_event(_event_key, _payload)
    ServiceResult.success
  end
end
