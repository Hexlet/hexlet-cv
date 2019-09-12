# frozen_string_literal: true

json.set! :@context, 'http://schema.org/'
json.set! :@type, 'Person'
json.set! :@id, resume_path(@resume)
json.set! :jobTitle, @resume.name
json.set! :familyName, @resume.user.last_name
json.set! :givenName, @resume.user.first_name
json.set! :alumniOf, @resume_educations, :institution
json.set! :worksFor, @resume_works, :company
