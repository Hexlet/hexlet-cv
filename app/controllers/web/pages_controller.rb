# frozen_string_literal: true

class Web::PagesController < Web::ApplicationController
  def show
    page = params[:id].to_sym
    description = t(".#{page}_description")
    url = page_url(page)
    title page

    set_meta_tags description: description,
                  canonical: url
    set_meta_tags og: {
      title: title,
      description: description,
      type: 'website',
      url: url
    }

    render params[:id]
  end
end
