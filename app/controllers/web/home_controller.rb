# frozen_string_literal: true

class Web::HomeController < Web::ApplicationController
  def index
    @q = Resume.web.with_locale.ransack(params[:q])
    @resumes = @q.result(distinct: true).includes(:user).page(params[:page]).order(id: :desc)
    @page = params[:page]
    @tags = Resume.directions_tags

    set_meta_tags og: {
      description: t('.description'),
      canonical: root_url,
      type: 'website',
      url: root_url
    }
  end
end
