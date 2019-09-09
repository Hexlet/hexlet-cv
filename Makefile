test:
	bin/rails test

setup:
	cp -n .env.example .env || true
	bin/setup
	bin/rails db:fixtures:load

fixtures-load:
	bin/rails db:fixtures:load

clean:
	bin/rails db:drop

db-reset:
	bin/rails db:drop
	bin/rails db:create
	bin/rails db:migrate
	bin/rails db:fixtures:load

start:
	bundle exec heroku local

lint:
	bundle exec rubocop

linter-fix:
	bundle exec rubocop --auto-correct

deploy:
	git push heroku master

lsp-configure:
	bundle exec yard gems
	bundle exec solargraph bundle
	
docker-setup:
	docker-compose build
	docker-compose run --rm web /bin/bash -c " \
		RAILS_ENV=test bundle exec rails db:drop db:create db:migrate db:seed; \
		RAILS_ENV=development bundle exec rails db:create db:migrate db:seed; \
		yarn install"

test-ruby:
	docker-compose run --rm web /bin/bash -c " \
		bundle exec rails test"

server:
	docker-compose up

heroku-console:
	heroku run rails console

.PHONY: test

.PHONY: server

.PHONY: test-ruby

