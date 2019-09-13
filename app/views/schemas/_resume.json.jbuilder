# frozen_string_literal: true

json.set! :@context, 'http://schema.org/'
json.set! :@type, 'Person'
json.set! :@id, resume_path(@resume)
json.set! :jobTitle, job_title
json.set! :familyName, last_name
json.set! :givenName, first_name
json.set! :alumniOf, educations, :institution
json.set! :worksFor, works, :company
