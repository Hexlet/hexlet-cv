# frozen_string_literal: true

class PhoneValidator < ActiveModel::EachValidator
  def validate_each(record, _attribute, value)
    return if value.blank?

    phone = if value.match?(/^\+?\s*8/) && value.scan(/\d/).size == 11
              value.sub(/^\+?\s*8/, '+7')
            else
              value
            end

    return if Phonelib.valid?(phone)

    record.errors.add(:contact_phone, :invalid)
  end
end
