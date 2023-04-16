# frozen_string_literal: true

class ResumeAutoAnswerService
  class << self
    def evaluate_resume(resume)
      client = ApplicationContainer[:open_ai_helper]
      resume_summary = prepare_resume(resume)
      response_recommendation = client.send_content(I18n.t('open_ai_command.evaluate_resume'), resume_summary)
      response_covering_letter = client.send_content(I18n.t('open_ai_command.write_cover_letter'), resume_summary)
      response_edited_text = client.send_content(I18n.t('open_ai_command.edit_text'), resume_summary)

      content_recommendation = response_recommendation.dig('choices', 0, 'message', 'content')
      content_covering_letter = response_covering_letter.dig('choices', 0, 'message', 'content')
      content_edited_text = response_edited_text.dig('choices', 0, 'message', 'content')

      content = I18n.t('recommendation_open_ai', recommendation: content_recommendation, letter: content_covering_letter, edit_text: content_edited_text, scope: 'web.answers')

      ActiveRecord::Base.transaction do
        user = User.find_by(email: ENV.fetch('EMAIL_SPECIAL_USER'))
        attrs = { content: }
        Resume::AnswerMutator.create(resume, attrs, user)
        resume.evaluated_ai = true
        resume.save!
      end
    end

    def prepare_resume(resume)
      resume
        .serializable_hash
        .deep_symbolize_keys
        .slice(:name, :summary, :skills_description, :awards_description, :contact_phone, :contact_email)
        .values
        .join('\n')
    end
  end
  private_class_method :prepare_resume
end
