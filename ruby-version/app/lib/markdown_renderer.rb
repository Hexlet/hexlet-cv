# frozen_string_literal: true

class MarkdownRenderer < Redcarpet::Render::HTML
  include ActionView::Helpers::SanitizeHelper
  include ApplicationHelper

  def initialize(extensions = {})
    @extensions = extensions.merge(link_attributes: { target: '_blank' })
    super(@extensions)
  end

  def block_html(raw_html)
    tag = Nokogiri::HTML.fragment(raw_html).children.first

    if tag.name == 'summary'
      return sanitize(tag.to_html, tags: ['summary'])
    end

    if @options[:details] && tag.name == 'details'
      data = raw_html.match(%r{<details[^<]*>(?<inner_html>.*)</details>}m)
      content = unindent(data[:inner_html])
      new_inner_html = markdown2html(content, @options)

      tag.children = '%s'
      return format(tag.to_html, new_inner_html)
    end

    if @options[:nonce_script] && tag.name == 'script' && !@options[:escape_html]
      tag['nonce'] = @options[:nonce_script]
      return tag.to_html
    end

    parent_html_renderer.render raw_html
  end

  private

  def parent_html_renderer
    @parent_html_renderer ||= Redcarpet::Markdown.new(Redcarpet::Render::HTML.new(@options), @extensions)
  end

  def unindent(text)
    text.gsub(/^#{text.scan(/^[\t ]+/).min}/, '')
  end
end
