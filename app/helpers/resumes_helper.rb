# frozen_string_literal: true

module ResumesHelper
  def custom_localize(object, format, locale = I18n.locale)
    key_month = locale == :ru ? :months_nominative_case : :'date.month_names'
    format_date = I18n.t(:"date.formats.#{format}")
    result = format_date.to_s.gsub(/%(|\^)B/) do |match|
      case match
      when '%B' then I18n.t!(key_month, locale:, format: format_date)[object.mon]
      when '%^B' then I18n.t!(key_month, locale:, format: format_date)[object.mon].upcase
      end
    end
    object.strftime(result)
  end
end
