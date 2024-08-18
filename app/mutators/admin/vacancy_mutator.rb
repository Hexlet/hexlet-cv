# frozen_string_literal: true

class Admin::VacancyMutator
  class << self
    def cancel!(vacancy, params = {})
      user = vacancy.creator
      cancelation_reason = params[:cancelation_reason]
      vacancy.assign_attributes(cancelation_reason:)

      ActiveRecord::Base.transaction do
        vacancy.cancel!
        user.notifications.create!(kind: :vacancy_cancel, resource: vacancy)
      end

      vacancy.canceled?
    end

    def update!(vacancy, params = {})
      user = vacancy.creator
      vacancy_was_not_published = !vacancy.published?

      ActiveRecord::Base.transaction do
        vacancy.update!(params)
        if params[:state_event] == 'publish' && vacancy_was_not_published
          user.notifications.create!(kind: :vacancy_publish, resource: vacancy)
        end
      end

      true
    rescue ActiveRecord::RecordInvalid
      false
    end
  end
end
