setup:
	bin/setup

test:
	bin/rails test

server:
	bin/rails s

deploy:
	git push heroku master

.PHONY: test
