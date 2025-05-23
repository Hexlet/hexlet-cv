# frozen_string_literal: true

module SeoRedirectConcern
  extend ActiveSupport::Concern

  included do
    def redirect_not_founded_to(dest = root_path)
      yield
    rescue ActiveRecord::RecordNotFound
      f(:not_found, type: :warning, scope: '')
      redirect_to dest, status: :moved_permanently
    end
  end

  class_methods do
    def redirect_actions_when_not_founded_to(path_cb, options = {})
      handler = proc { |controller, action| redirect_not_founded_to(controller.instance_exec(&path_cb), &action) }

      around_action handler, options
    end
  end
end
