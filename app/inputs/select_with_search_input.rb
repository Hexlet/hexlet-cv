# frozen_string_literal: true

class SelectWithSearchInput < SimpleForm::Inputs::CollectionSelectInput
  def input_html_classes
    super.push('js-select')
  end
end
