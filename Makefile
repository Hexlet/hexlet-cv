setup:
	yarn install
	bundle install --without production
	bin/rails db:create db:migrate
	bin/rails db:fixtures:load

test:
	bin/rails test

start:
	bin/rails s

lint:
	bundle exec rubocop

fix:
	bundle exec rubocop --auto-correct

deploy:
	git push heroku master

lsp-configure:
	bundle exec yard gems
	bundle exec solargraph bundle

.PHONY: test
