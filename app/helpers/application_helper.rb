# frozen_string_literal: true

module ApplicationHelper
  # include AuthManagement

  def han(model_name, attribute_name)
    model_name.classify.constantize.human_attribute_name(attribute_name)
  end

  def markdown2html(text)
    extensions = {
      autolink: true,
      filter_html: true,
      safe_links_only: true
    }
    markdown = Redcarpet::Markdown.new(Redcarpet::Render::HTML, extensions)
    markdown.render(text)
  end
end
