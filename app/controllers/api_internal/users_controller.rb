# frozen_string_literal: true

class ApiInternal::UsersController < ApiInternal::ApplicationController
  def search
    search = User.ransack(first_name_or_last_name_or_email_cont: params[:term])
    @users = search.result.limit(20)
  end
end
