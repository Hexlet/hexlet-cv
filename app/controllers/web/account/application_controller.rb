# frozen_string_literal: true

class Web::Account::ApplicationController < ApplicationController
  before_action :authenticate_user!
end
