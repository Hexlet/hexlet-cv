# frozen_string_literal: true

class ApplicationContainer
  extend Dry::Container::Mixin

  if Rails.env.test?
    register(:open_ai_helper, memoize: true) { OpenAiHelperStub.new }
    register(:n8n_client) { N8nClientStub }
  else
    register(:open_ai_helper, memoize: true) { OpenAiHelper.new }
    register(:n8n_client) { N8nClient }
  end
end
