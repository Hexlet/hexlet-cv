# frozen_string_literal: true

class Web::Admin::HomeController < Web::Admin::ApplicationController
  def index
    @q = User.ransack
    @admins = @q.result.with_role(:admin)
  end
end
