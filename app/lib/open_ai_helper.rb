# frozen_string_literal: true

class OpenAiHelper
  attr_reader :client

  MODEL = 'gpt-3.5-turbo'
  TEMPERATURE = 0.7

  def initialize
    @client = OpenAI::Client.new
  end

  def send_content(prompt, resume_content)
    response = chat([
                      { role: 'system', content: I18n.t('open_ai_command.system_command') },
                      { role: 'user', content: prompt },
                      { role: 'user', content: resume_content }
                    ])

    raise StandardError, response['error']['message'] if response['error']

    response
  end

  private

  def chat(messages = [])
    client.chat(
      parameters: {
        model: MODEL,
        messages:,
        temperature: TEMPERATURE
      }
    )
  end
end
