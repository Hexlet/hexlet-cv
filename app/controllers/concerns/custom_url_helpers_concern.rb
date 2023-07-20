# frozen_string_literal: true

module CustomUrlHelpersConcern
  def hexlet_profession_with_employment
    'https://ru.hexlet.io/courses?program_filter_form%5Bemployment%5D=1#professions'
  end

  def amocrm_lead_search_curl(email)
    "https://hexlet.amocrm.ru/leads/list/?skip_filter=Y&term=#{email}"
  end
end
