# frozen_string_literal: true

class Admin::VacancyMutator
  NOTIFIED_EVENTS = %w[publish].freeze

  class << self
    def cancel!(vacancy, params = {})
      user = vacancy.creator
      cancelation_reason = params[:cancelation_reason]
      vacancy.assign_attributes(cancelation_reason:)

      ActiveRecord::Base.transaction do
        vacancy.cancel!
        user.notifications.create!(kind: :vacancy_cancel, resource: vacancy)
      end

      vacancy
    end

    def update!(vacancy, params = {})
      event = params[:state_event]

      if event.nil? || NOTIFIED_EVENTS.exclude?(event)
        vacancy.update!(params)
        return true
      end

      user = vacancy.creator

      ActiveRecord::Base.transaction do
        vacancy.update!(params)
        user.notifications.create!(kind: "vacancy_#{event}", resource: vacancy)
      end

      true
    rescue ActiveRecord::RecordInvalid
      false
    end
  end
end
