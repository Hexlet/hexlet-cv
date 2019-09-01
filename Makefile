setup:
	bin/setup
	bin/rails db:fixtures:load

test:
	bin/rails test

start:
	bin/rails s

lint:
	bundle exec rubocop

deploy:
	git push heroku master

lsp-configure:
	bundle exec yard gems
	bundle exec solargraph bundle

.PHONY: test
