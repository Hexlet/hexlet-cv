# frozen_string_literal: true

class Web::Resumes::PdfsController < Web::Resumes::ApplicationController
  def show
    authorize resource_resume, :download?

    templates = %w[base]

    unless templates.include?(params[:id])
      raise ActionController::RoutingError, "Cannot find page '#{params[:id]}'"
    end

    @resume_educations = resource_resume.educations.web
    @resume_works = resource_resume.works.web

    template_html = render_to_string(params[:id], layout: 'pdf')
    pdf = WickedPdf.new.pdf_from_string(template_html)

    respond_to do |format|
      format.html do
        send_data(pdf, filename: 'hexlet-cv-resume.pdf', disposition: 'attachment')
      end
      format.pdf do
        render template: "web/resumes/pdfs/#{params[:id]}",
               pdf: params[:id],
               layout: 'pdf'
      end
    end
  end
end
