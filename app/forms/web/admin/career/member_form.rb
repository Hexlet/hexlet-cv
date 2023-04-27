# frozen_string_literal: true

class Web::Admin::Career::MemberForm < Career::Member
  include ActiveFormModel

  fields :user_id,
         :career_id
end
