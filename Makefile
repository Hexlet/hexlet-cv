setup:
	bin/setup
	bin/rails db:fixtures:load

test:
	bin/rails test

start:
	bin/rails s

deploy:
	git push heroku master

.PHONY: test

compose:
	docker-compose up

compose-setup: compose-build compose-bundle-install compose-yarn-install compose-db-prepare compose-load-fixtures

compose-build:
	docker-compose build

compose-bash:
	docker-compose run app bash

compose-bundle-install:
	docker-compose run app bundle install

compose-yarn-install:
	docker-compose run app yarn install

compose-db-prepare:
	docker-compose run app bash -c "bin/rails db:create db:migrate"

compose-load-fixtures: 
	docker-compose run app bin/rails db:fixtures:load

compose-test:
	docker-compose run app bin/rails test
