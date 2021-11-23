test:
	bin/rails test

prepare:
	npm install --global yarn
	curl https://cli-assets.heroku.com/install.sh | sh

setup:
	cp -n .env.example .env || true
	bin/setup
	bin/rails db:fixtures:load
	npx simple-git-hooks

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
	heroku local

lint: lint-rubocop lint-slim
lint-fix: lint-rubocop-fix

lint-rubocop:
	bundle exec rubocop

lint-rubocop-fix:
	bundle exec rubocop -A

lint-slim:
	bundle exec slim-lint app/**/*.slim

deploy:
	git push heroku master

lsp-configure:
	bundle exec yard gems
	bundle exec solargraph bundle

heroku-console:
	heroku run rails console

heroku-logs:
	heroku logs --tail

ci-setup:
	cp -n .env.example .env || true
	yarn install
	bundle install --without production development
	RAILS_ENV=test bin/rails db:prepare
	# bin/rails db:fixtures:load

check: lint test

ci-setup-check: ci-setup check

.PHONY: test
