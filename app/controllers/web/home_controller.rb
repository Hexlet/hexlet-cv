# frozen_string_literal: true

class Web::HomeController < Web::ApplicationController
  def index
    q = Resume.web.ransack(params[:q])
    @resumes = q.result(distinct: true).page(params[:page])
    @resume_search_form = Web::Resumes::SearchForm.new
    @tags = Resume.tags_sorted_list

    set_meta_tags og: {
      description: t('.description'),
      canonical: root_url,
      type: 'website',
      url: root_url
    }
  end
end
