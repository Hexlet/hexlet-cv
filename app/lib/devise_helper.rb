# frozen_string_literal: true

module DeviseHelper
  def after_sign_out_path_for(resource_or_scope)
    scope = Devise::Mapping.find_scope!(resource_or_scope)
    router_name = Devise.mappings[scope].router_name
    context = router_name ? send(router_name) : self
    context.respond_to?(:root_path) ? context.root_path(locale:) : root_path(locale:)
  end

  def signed_in_root_path(resource_or_scope)
    scope = Devise::Mapping.find_scope!(resource_or_scope)
    router_name = Devise.mappings[scope].router_name

    home_path = "#{scope}_root_path"

    context = router_name ? send(router_name) : self

    if context.respond_to?(home_path, true)
      context.send(home_path)
    elsif context.respond_to?(:root_path)
      context.root_path(locale:)
    elsif respond_to?(:root_path)
      root_path(locale:)
    else
      "/#{locale}"
    end
  end
end
