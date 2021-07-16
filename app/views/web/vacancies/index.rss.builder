# frozen_string_literal: true

xml.instruct! :xml, version: '1.0'
xml.rss(version: '2.0', 'xmlns:atom': 'https://www.w3.org/2005/Atom') do
  xml.channel do
    xml.title t('.title')
    xml.description t('.description')
    xml.link root_url

    @vacancies.each do |vacancy|
      xml.item do
        xml.title vacancy
        xml.description truncate_markdown(vacancy.description, length: 200)
        xml.pubDate vacancy.created_at.to_s(:rfc822)
        xml.link vacancy_url(vacancy)
        xml.guid vacancy_url(vacancy)
      end
    end
  end
end
