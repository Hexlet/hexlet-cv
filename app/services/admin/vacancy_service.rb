# frozen_string_literal: true

class Admin::VacancyService
  NOTIFIED_EVENTS = %w[cancele publish].freeze

  class << self
    def call(vacancy, params = {})
      event = params[:state_event]

      if event.nil? || !notified_event?(event)
        vacancy.update!(params)
        return result_success
      end

      user = vacancy.creator

      ActiveRecord::Base.transaction do
        vacancy.update!(params)
        user.notifications.create!(kind: "vacancy_#{event}", resource: vacancy)
      end

      result_success
    rescue StandardError
      result_fail
    end

    def notified_event?(event)
      NOTIFIED_EVENTS.include?(event)
    end

    def result_success
      ServiceResult.success
    end

    def result_fail
      ServiceResult.fail
    end
  end

  private_class_method :notified_event?, :result_success, :result_fail
end
