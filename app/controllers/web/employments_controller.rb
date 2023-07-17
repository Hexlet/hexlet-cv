# frozen_string_literal: true

class Web::EmploymentsController < Web::ApplicationController
  def show
    @lead = Web::LeadForm.new
    set_meta_tags canonical: employment_url
    # set_meta_tags og: {
    #   title: @resume,
    #   description: @resume.summary,
    #   type: 'article',
    #   url: resume_url(@resume)
    # }
  end
end
