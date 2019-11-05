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
    renderer = Redcarpet::Render::HTML.new(hard_wrap: true, link_attributes: { target: '_blank' })
    markdown = Redcarpet::Markdown.new(renderer, extensions)
    markdown.render(text)
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
    assembled_options = options.merge(class: "nav-link #{active?(path)}")
    content_tag :li, class: 'nav-item' do
      link_to path, assembled_options do
        content_tag(:div, name)
      end
    end
  end

  def message_for_notification(notification)
    ::NotificationsHelper.message(notification)
  end

  def icon_class_for_notification(notification)
    ::NotificationsHelper.notification_icon(notification)
  end

  def filter_link(name, path = {}, options = {})
    filter_path = { q: path }
    active = current_page?(filter_path, check_parameters: true)
    assembled_options = options.merge(class: [options[:class], active?(filter_path, active_if: active)].join(' '))
    link_to_unless active, name, filter_path, assembled_options do
      content_tag :span, name, assembled_options
    end
  end
end
