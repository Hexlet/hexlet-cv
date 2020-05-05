# frozen_string_literal: true

class Web::Admin::ApplicationController < Web::ApplicationController
  include Auth::Admin

  before_action :authenticate_admin!
end
