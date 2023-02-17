# frozen_string_literal: true

class DirectionListValidator < ActiveModel::EachValidator
  def validate_each(record, _attribute, value)
    record.errors.add(:direction_list, I18n.t('.inputs.max_length', max_length: TAG_MAX_LENGTH)) unless value.grep(/.{#{TAG_MAX_LENGTH},}/o).empty?
  end
end
