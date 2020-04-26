# frozen_string_literal: true

class Web::Admin::ApplicationController < ApplicationController
  include Auth::Admin

  layout 'admin'

  before_action :authenticate_admin!
end
