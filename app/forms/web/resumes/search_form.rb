# frozen_string_literal: true

class Web::Resumes::SearchForm
  include ActiveFormModel::Virtual

  ATTRIBUTES = %i[
    name_cont
    user_first_name_or_user_last_name_or_user_email_cont
    directions_name_cont
    created_at_gteq
    popular_gteq
    created_at_gteq
    s
    answers_count_eq
    answers_count_in
    answers_user_id_eq
  ].freeze

  fields(*ATTRIBUTES)

  def to_h
    attrs = {}
    ATTRIBUTES.each do |attribute|
      attrs[attribute] = public_send(attribute)
    end

    attrs
  end
end
