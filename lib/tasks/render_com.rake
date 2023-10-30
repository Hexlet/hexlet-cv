# frozen_string_literal: true

require 'active_record'
require 'active_record/fixtures'
require 'faker'

namespace :render_com do
  desc 'Seed database with fixtures for render.com'
  task load_fixtures: :environment do
    render_load_fixtures
  end

  # rubocop:disable Rails
  def render_load_fixtures
    # render.com seeding resolve
    # WARNING: Rails was not able to disable referential integrity.
    # This is most likely caused due to missing permissions.
    # Rails needs superuser privileges to disable referential integrity.
    #
    #     cause: PG::InsufficientPrivilege: ERROR:  permission denied: "RI_ConstraintTrigger_a_16826" is a system trigger
    #
    # rails aborted!
    # ActiveRecord::InvalidForeignKey: PG::ForeignKeyViolation: ERROR:  insert or update on table "taggings" violates foreign key constraint "fk_rails_9fcd2e236b"

    fixture_path = Rails.root.join('test/fixtures')

    fixtures = %w[
      acts_as_taggable_on/tags
      acts_as_taggable_on/taggings
      users
      vacancies
      notifications
      careers
      career/members
      career/steps
      career/step/members
      career/items
      resumes
      resume/answers
      resume/answer/comments
      resume/answer/likes
      resume/comments
      resume/educations
      resume/works
    ]

    puts '#############'
    puts 'Seeding start'

    longest_table_name = 0
    fixtures.each do |path|
      table = path_to_class(path)
      longest_table_name = table.table_name.size if table.table_name.size > longest_table_name
    end

    ActiveRecord::Base.transaction do
      fixture_error_foreign = 'Foreign key violations found in your fixture data'

      fixtures.each do |path|
        table = path_to_class(path)
        next if table.count.positive?

        printf "%-#{longest_table_name}s", table.table_name
        fixture_file = "#{fixture_path}/#{path}.yml"
        ActiveRecord::FixtureSet.create_fixtures(fixture_path, path) if File.exist?(fixture_file)
        printf "\n"
      rescue RuntimeError => e
        if e.full_message.include?(fixture_error_foreign)
          puts " : #{fixture_error_foreign}, that's ok, on render.com"
        else
          puts " : ↓↓↓ REAL ERROR ↓↓↓\n\t\t#{e.full_message}\n\n"
        end
      end
    end

    puts 'Seeding stop'
    puts '############'
  end

  # rubocop:enable Rails

  def path_to_class(path)
    path.split('/').map(&:camelize).join('::').singularize.constantize
  end
end
