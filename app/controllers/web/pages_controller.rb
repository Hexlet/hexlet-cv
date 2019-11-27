# frozen_string_literal: true

class Web::PagesController < ApplicationController
  def show
    title params[:id].to_sym

    set_meta_tags description: t(".#{params[:id]}_description"),
                  canonical: pages_url('about')
    set_meta_tags og: {
      title: title,
      description: t(".#{params[:id]}_description"),
      type: 'website',
      url: pages_url('about')
    }

    render params[:id]
  end
end
