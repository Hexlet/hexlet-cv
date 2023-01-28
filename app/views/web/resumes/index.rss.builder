# frozen_string_literal: true

xml.instruct! :xml, version: '1.0'
xml.rss(version: '2.0', 'xmlns:atom': 'https://www.w3.org/2005/Atom') do
  xml.channel do
    xml.title t('.title')
    xml.description t('.description')
    xml.link root_url

    @resumes.each do |resume|
      xml.item do
        xml.title resume
        xml.description truncate_markdown(resume.summary, length: 200)
        xml.pubDate resume.created_at.to_fs(:rfc822)
        xml.link resume_url(resume)
        xml.guid resume_url(resume)
      end
    end
  end
end
