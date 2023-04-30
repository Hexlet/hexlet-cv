# frozen_string_literal: true

module CsvConcern
  def generate_csv(records, headers)
    Enumerator.new do |csv|
      csv << headers.to_csv
      records.find_each do |record|
        csv << yield(record).to_csv if block_given?
      end
    end
  end
end
