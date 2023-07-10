# frozen_string_literal: true

class Web::Careers::EmploymentsController < Web::Careers::ApplicationController
  def show
    set_meta_tags canonical: employment_url
    # set_meta_tags og: {
    #   title: @resume,
    #   description: @resume.summary,
    #   type: 'article',
    #   url: resume_url(@resume)
    # }
  end
end
