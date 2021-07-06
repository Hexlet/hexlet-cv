# frozen_string_literal: true

class Web::HomeController < Web::ApplicationController
  def index
    q = Resume.web.ransack(params[:q])
    @resumes = q.result(distinct: true).page(params[:page])

    set_meta_tags description: t('.description'),
                  canonical: root_url
    set_meta_tags og: {
      title: t('titles.web.base'),
      description: t('.description'),
      type: 'website',
      url: root_url
    }
  end
end
