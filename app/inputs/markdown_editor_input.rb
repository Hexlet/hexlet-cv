# frozen_string_literal: true

class MarkdownEditorInput < SimpleForm::Inputs::TextInput
  def input_html_options
    options = super
    options['data-provide'] = 'hexlet-markdown'
    options[:rows] ||= 10
    options
  end
end
