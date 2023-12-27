# frozen_string_literal: true

class Web::HomeController < Web::ApplicationController
  def index
    query = { s: 'id desc' }.merge(params.permit![:q] || {})
    sanitize_query = if current_user_or_guest.admin?
                       query
                     else
                       query.except('answers_count_eq', 'answers_count_in', 'answers_user_id_eq')
                     end
    form = Web::Resumes::SearchForm.new(sanitize_query)
    @bot = AiBotHelper.ai_bot_user
    @search = Resume.web.with_locale.ransack(form.to_h)
    @resumes = @search.result(distinct: true).includes(:user, :skills).page(params[:page])
    @page = params[:page]
    @tags = Resume.directions_tags

    set_meta_tags title: t('titles.web.base'),
                  canonical: root_url,
                  og: {
                    description: t('.description'),
                    type: 'website',
                    title: t('titles.web.base'),
                    url: root_url
                  }
  end
end
