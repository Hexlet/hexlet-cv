# frozen_string_literal: true

class Web::Devise::SessionsController < Devise::SessionsController
  include GonInit
  include LocaleConcern
end
