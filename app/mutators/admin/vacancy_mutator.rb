# frozen_string_literal: true

class Admin::VacancyMutator
  class << self
    def cancel!(vacancy, params = {})
      user = vacancy.creator
      cancelation_reason = params[:cancelation_reason]
      vacancy.assign_attributes(cancelation_reason:)

      if vacancy.cancel!
        user.notifications.create!(kind: :vacancy_cancel, resource: vacancy)
      end

      vacancy
    end

    def update(vacancy, params = {})
      user = vacancy.creator
      may_create_notification = params[:state_event] == 'publish' && !vacancy.published?

      if vacancy.update(params) && may_create_notification
        user.notifications.create!(kind: :vacancy_publish, resource: vacancy)
      end

      vacancy
    end
  end
end
