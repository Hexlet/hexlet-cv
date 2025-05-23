# frozen_string_literal: true

# NOTE: Config via env variable SENTRY_DSN
Sentry.init do |config|
  config.breadcrumbs_logger = %i[active_support_logger http_logger]

  config.send_default_pii = true
  config.send_modules = false
  config.rails.report_rescued_exceptions = true

  config.excluded_exceptions += [
    'ActionController::RoutingError',
    'ActionController::UnknownFormat',
    'ActiveRecord::RecordNotFound',
    'Faraday::ConnectionFailed',
    'Pundit::NotAuthorizedError',
    'Mime::Type::InvalidMimeType'
  ]

  config.traces_sampler = lambda do |sampling_context|
    # transaction_context is the transaction object in hash form
    # keep in mind that sampling happens right after the transaction is initialized
    # for example, at the beginning of the request
    transaction_context = sampling_context[:transaction_context]

    # transaction_context helps you sample transactions with more sophistication
    # for example, you can provide different sample rates based on the operation or name
    op = transaction_context[:op]
    transaction_name = transaction_context[:name]

    case op
    when /http/
      # for Rails applications, transaction_name would be the request's path (env["PATH_INFO"]) instead of "Controller#action"
      case transaction_name
      when /health_check/
        0.0
      else
        0.001
      end
    when /sidekiq/
      0.0001 # you may want to set a lower rate for background jobs if the number is large
    else
      0.0 # ignore all other transactions
    end
  end
end
