# frozen_string_literal: true

class Web::LeadsController < Web::ApplicationController
  def create
    @lead = Web::LeadForm.new(params[:web_lead_form])
    if @lead.save
      f(:success)
      redirect_to root_path
    else
      f(:success_existing_user, type: :success)
      redirect_to new_user_session_path
    end
  end
end
