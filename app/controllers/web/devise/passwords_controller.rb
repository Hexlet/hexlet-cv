# frozen_string_literal: true

class Web::Devise::PasswordsController < Devise::PasswordsController
  include LocaleConcern
  include DeviseHelper
end
