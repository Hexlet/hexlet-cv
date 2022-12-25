# frozen_string_literal: true

class StridesValidator < ActiveModel::Validator
  def validate(record)
    record.strides.each do |stride|
      record.errors.add :strides, "Unknown stride: #{stride}" unless stride.in? Career::GOALS
    end
  end
end
