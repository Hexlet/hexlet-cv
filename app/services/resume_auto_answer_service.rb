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
        attrs = { content: }
        bot = AiBotHelper.ai_bot_user
        @answer = Resume::AnswerMutator.create(resume, attrs, bot)
        resume.mark_as_evaluated!
      end
      EmailSender.send_new_answer_mail(@answer) if @answer&.persisted?
    rescue StandardError => e
      Rails.logger.error("#{e.class}: #{e.message}")
      resume.mark_as_failed!
      raise e
    end

    def prepare_resume(resume)
      resume_content = resume
                       .serializable_hash(except: %i[hexlet_url awards_description])
                       .deep_symbolize_keys
                       .slice(:name, :summary, :projects_description, :about_myself, :skills_description, :contact_phone, :contact_email)
                       .values
                       .join('\n')
      work_content = resume.works.reduce('') do |acc, work|
        content = work.serializable_hash.deep_symbolize_keys.slice(:company, :position, :begin_date, :end_date, :description)
        acc += I18n.t('format_for_openAI.work', company: content[:company], position: content[:position], begin_date: content[:begin_date], end_date: content[:end_date], description: content[:description])
        "#{acc}\n"
      end
      education_content = resume.educations.reduce('') do |acc, education|
        content = education.serializable_hash.deep_symbolize_keys.slice(:institution, :faculty, :begin_date, :end_date, :description)
        acc += I18n.t('format_for_openAI.education', institution: content[:institution], faculty: content[:faculty], begin_date: content[:begin_date], end_date: content[:end_date], description: content[:description])
        "#{acc}\n"
      end
      "#{resume_content}\n#{work_content}\n#{education_content}"
    end
  end
  private_class_method :prepare_resume
end
