# frozen_string_literal: true

class Web::Admin::ApplicationController < Web::ApplicationController
  include CsvConcern
  include ApplicationHelper
  before_action :authenticate_admin!
end
