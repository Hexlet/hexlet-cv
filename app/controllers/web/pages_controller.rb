# frozen_string_literal: true

class Web::PagesController < Web::ApplicationController
  def show
    page = params[:id].to_sym
    description = t(".#{page}_description")
    url = page_url(page)

    set_meta_tags title: t(".#{page}_title"),
                  description: description,
                  canonical: url
    set_meta_tags og: {
      title: t(".#{page}_title"),
      description:,
      type: 'website',
      url:
    }

    render params[:id]
  end
end
