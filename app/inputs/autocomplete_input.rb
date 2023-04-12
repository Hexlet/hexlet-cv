# frozen_string_literal: true

class AutocompleteInput < SimpleForm::Inputs::CollectionSelectInput
  def collection
    reflection ? [object.send(:"#{reflection.name}")] : []
  end

  def input(wrapper_options = {})
    template.append_javascript_packs('autocomplete')
    input_html_options['data-ajax--url'] = options[:source]
    super
  end

  def input_html_classes
    super.push('select2')
  end
end
