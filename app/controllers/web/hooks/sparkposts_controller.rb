# frozen_string_literal: true

# TODO: move to api
class Web::Hooks::SparkpostsController < ApplicationController
  def create
    type = params[:msys][:message_event][:type]
    method = "handle_#{type}"
    send(method, params) if sparkpost_types.include?(type)
  end
end
