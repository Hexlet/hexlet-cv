include make-compose.mk

test:
	bin/rails test

frontend:
	npx nodemon -L --watch webpack.config.js --exec npm run build:watch

setup-heroku:
	curl https://cli-assets.heroku.com/install.sh | sh

setup: setup-heroku	

env-prepare:
	cp -n .env.example .env	

setup:
	setup-heroku
	make env-prepare
	bin/setup
	bin/rails db:fixtures:load
	npm ci
	npm run build
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
	heroku local -p 3000

lint: lint-code lint-style

lint-code:
	bundle exec rubocop
	bundle exec slim-lint app/views/
	# TODO: add eslint

lint-style:
	npx stylelint "**/*.scss" "!**/vendor/**"

linter-code-fix:
	bundle exec rubocop -A

deploy:
	git push heroku main

lsp-configure:
	bundle exec yard gems
	bundle exec solargraph bundle

heroku-console:
	heroku run rails console

heroku-logs:
	heroku logs --tail

ci-setup:
	make env-prepare
	npm ci
	npm run build
	bundle
	# yarn install
	# bundle install --without production development
	# RAILS_ENV=test bin/rails db:prepare
	# bin/rails db:fixtures:load

check: lint test

ci-setup-check: ci-setup check

.PHONY: test
