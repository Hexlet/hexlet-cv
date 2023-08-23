# frozen_string_literal: true

class VacancyDirectionListValidator < ActiveModel::EachValidator
  def validate_each(record, _attribute, value)
    record.errors.add(:direction_list, I18n.t('.inputs.invalid_tag', invalid_tags: value.grep(/^\w+\.(js|php|html)$/o).join(', '))) unless value.grep(/^.+\.(js|php|html)$/o).empty?
  end
end
