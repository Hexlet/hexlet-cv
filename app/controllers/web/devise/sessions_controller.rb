# frozen_string_literal: true

class Web::Devise::SessionsController < Devise::SessionsController
  include GonInit
  include LocaleConcern

  def after_sign_out_path_for(_resource_or_scope)
    root_path(locale)
  end
end
