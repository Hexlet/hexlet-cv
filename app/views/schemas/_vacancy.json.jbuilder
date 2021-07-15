# frozen_string_literal: true

json.set! :@context, 'http://schema.org/'
json.set! :@type, 'JobPosting'
# json.set! :@id, vacancy_path(vacancy)
json.set! :title, vacancy.title
json.set! :description, vacancy.description
json.set! :validThrough, l(vacancy.created_at + 1.month, format: :schema)
json.set! :hiringOrganization do
  json.set! :@type, 'Organization'
  json.set! :name, vacancy.company_name
  json.set! :sameAs, vacancy.site
end
# json.set! :JobLocation, vacancy.location
json.set! :employmentType, 'full-time'
if vacancy.salary_from?
  json.set! :baseSalary do
    json.set! :@type, 'MonetaryAmount'
    json.set! :currency, 'RUB'
    json.set! :name, vacancy.company_name
    json.set! :value do
      json.set! :@type, 'QuantitativeValue'
      json.set! :value, vacancy.salary_from
      json.set! :unitText, 'MONTH'
    end
  end
end
json.set! :datePosted, l(vacancy.created_at, format: :schema)

json.set! :jobLocation do
  json.set! :@type, :Place
  json.set! :address do
    json.set! :@type, :PostalAddress
    json.set! :addressLocality, vacancy.location
  end
end

json.set! :identifier do
  json.set! :@type, :PropertyValue
  json.set! :name, vacancy.company_name
  json.set! :value, vacancy.id
end
