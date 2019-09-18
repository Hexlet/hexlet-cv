# frozen_string_literal: true

class Web::Answers::ApplicationController < ApplicationController
  before_action :authenticate_user!
  helper_method :resource_answer

  def resource_answer
    @resource_answer ||= Resume::Answer.find(params[:answer_id])
  end
end
