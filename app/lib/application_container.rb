# frozen_string_literal: true

class ApplicationContainer
  extend Dry::Container::Mixin

  if Rails.env.test?
    register(:open_ai_helper, memoize: true) { OpenAiHelperStub.new }
  else
    register(:open_ai_helper, memoize: true) { OpenAiHelper.new }
  end
end
