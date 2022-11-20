# frozen_string_literal: true

module ApplicationHelper
  # include AuthManagement
  def let(value)
    yield value
  end

  def han(model_name, attribute_name)
    model_name.classify.constantize.human_attribute_name(attribute_name)
  end

  def markdown2html(text)
    extensions = {
      autolink: true,
      filter_html: true,
      safe_links_only: true
    }

    options = {
      escape_html: true,
      hard_wrap: true,
      link_attributes: { target: '_blank' }
    }

    renderer = Redcarpet::Render::HTML.new(options)
    markdown = Redcarpet::Markdown.new(renderer, extensions)
    markdown.render(text)
  end

  def truncate_markdown(text, options)
    truncate(strip_tags(markdown2html(text)), options)
  end

  def active?(path, options = {})
    # raise options.inspect
    if options.key? :active_if
      'active' if options[:active_if]
    elsif current_page?(path)
      'active'
    end
  end

  def nav_menu_item(name, path = '#', options = {})
    assembled_options = options.merge(class: "nav-link link-dark #{active?(path)}")
    tag.li class: 'nav-item' do
      link_to path, assembled_options do
        tag.div(name)
      end
    end
  end

  def message_for_notification(notification)
    ::NotificationsHelper.message(notification)
  end

  def class_for_notification(notification)
    ::NotificationsHelper.notification_class(notification)
  end

  def not_found_random_image
    # TODO: add funny pictures
    tag.div class: 'lead text-center' do
      t('.nothing')
    end
  end

  def filter_link(name, path = {}, options = {})
    filter_path = { q: path }
    active = current_page?(filter_path, check_parameters: true)
    assembled_options = options.merge(class: [options[:class], 'link-dark', active?(filter_path, active_if: active)])
    link_to name, filter_path, assembled_options
  end

  def structured_data_tag(path, args = {})
    content = render partial: "schemas/#{path}", formats: [:json], locals: args
    # rubocop:disable Rails/OutputSafety
    tag.script(content.html_safe, type: 'application/ld+json')
    # rubocop:enable Rails/OutputSafety
  end

  def default_filter_form_options(options = {})
    { method: 'get', html: { class: 'row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-2 g-lg-3' }, url: url_for, builder: SimpleForm::FormBuilder, wrapper: 'filter_form', defaults: { required: false } }.merge(options)
  end

  def filter_slug(options)
    options
      .sort
      .map { |k, v| { k => k == :city ? Slug.encode(v) : v } }.reduce(:merge)
      .compact
      .map { |k, v| "#{k}-#{v.downcase}" }
      .join('_')
  end

  def seo_for_paging(number_page, text)
    return text if number_page.nil?

    "#{t('page', number: number_page)}-#{text}"
  end
end
