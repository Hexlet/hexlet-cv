# frozen_string_literal: true

class HabrClient
  HEADER = {
    'Content-Type': 'application/json; charset=utf-8'
  }.freeze

  ACCESS_TOKEN = ENV.fetch('HABR_ACCESS_TOKEN')

  BASE_URI = 'https://career.habr.com/api/v1'

  METHODS = {
    vacansies: 'vacancies'
  }.freeze

  FILTERS = {
    specializations: {
      backend: 2,
      frontend: 3,
      web: 82,
      full_stack: 4,
      qa_manual: 12,
      qa_auto: 10,
      analitic: 43,
      web_analitic: 100
    },
    qualifications: {
      intern: 1,
      junior: 2
    },
    skills: {
      js: 264,
      html: 1017,
      css: 32,
      react: 1070,
      java: 1012,
      php: 1005,
      typescript: 245,
      web_dev: 318,
      nodejs: 12,
      redux: 1106,
      python: 446,
      ruby_on_rails: 1080
    }

  }.freeze

  def self.request
    url = "#{BASE_URI}/#{METHODS[:vacansies]}"
    uri = add_params(url,
                     access_token: ACCESS_TOKEN, skip_with_salary: 0,
                     skip_company_filled: 0,
                     's[]': FILTERS[:specializations].to_a.map(&:last),
                     'qid[]': FILTERS[:qualifications].to_a.map(&:last))
    response = Net::HTTP.get_response(uri, HEADER)

    return ServiceResult.fail(JSON.parse(response.body)) unless response.is_a?(Net::HTTPSuccess)

    ServiceResult.success(JSON.parse(response.body))
  end

  def self.add_params(url, params = {})
    uri = URI(url)
    params = URI.decode_www_form(uri.query || '').to_h.merge(params)
    uri.query = URI.encode_www_form(params)
    uri
  end

  private_class_method :add_params
end
