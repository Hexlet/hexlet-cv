# frozen_string_literal: true

module BootstrapHelper
  def menu_item(name = nil, path = '#', *args, &block)
    path = name || path if block
    args_options = args.extract_options!
    options = { class: "nav-link text-body px-3 #{active?(path)}" }.merge args_options
    tag.li class: 'nav-item' do
      if block
        name = path
        path = options
      end
      link_to name, path, options, &block
    end
  end
end
