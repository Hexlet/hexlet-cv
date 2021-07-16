# frozen_string_literal: true

class Web::ErrorsController < Web::ApplicationController
  def not_found; end

  def server_error; end

  def forbidden; end
end
