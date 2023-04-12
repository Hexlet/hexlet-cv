# frozen_string_literal: true

class ApiInternal::ApplicationController < ActionController::API
  include Auth

  respond_to :json
end
