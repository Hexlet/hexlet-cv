# frozen_string_literal: true

module CustomUrlHelpersConcern
  def hexlet_courses_curl
    'https://ru.hexlet.io/courses?promo_name=courses&promo_position=body&promo_type=card&promo_creative=employment'
  end

  def amocrm_lead_search_curl(email)
    "https://hexlet.amocrm.ru/leads/list/?skip_filter=Y&term=#{email}"
  end
end
