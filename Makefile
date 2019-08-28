setup:
	bin/setup
	bin/rails db:fixtures:load

test:
	bin/rails test

server:
	bin/rails s

deploy:
	git push heroku master

.PHONY: test
