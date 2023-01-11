# frozen_string_literal: true

Date::DATE_FORMATS[:month_and_year] = ->(time) { time.strftime("#{I18n.t(:months_nominative_case)[time.mon]} %Y") }
