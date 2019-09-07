# frozen_string_literal: true

class Web::Answers::ApplicationController < ApplicationController
  before_action :authenticate_user!
end

