setup:
	bin/setup
	bundle exec yard gems
	bundle exec solargraph bundle
	bin/rails db:fixtures:load

test:
	bin/rails test

start:
	bin/rails s

lint:
	bundle exec rubocop

deploy:
	git push heroku master

.PHONY: test
