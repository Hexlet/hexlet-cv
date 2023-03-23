compose:
	docker-compose up -d

compose-build:
	docker-compose build

compose-bash:
	docker-compose run --rm app bash

compose-clear:
	docker-compose down -v --remove-orphans || true

compose-down:
	docker-compose down || true

compose-install:
	docker-compose run --rm app make setup

compose-install-npm:
	docker-compose run --rm app npm install --force

compose-install-bundle:
	docker-compose run --rm app bundle install --jobs $(shell nproc)

compose-lint:
	docker-compose run --rm app make lint

compose-lint-fix:
	docker-compose run --rm app make linter-code-fix

compose-lint-eslint:
	docker-compose run --rm app make lint-eslint

compose-lint-eslint-fix:
	docker-compose run --rm app make lint-eslint-fix

compose-logs:
	docker-compose logs -f

compose-rails-console:
	docker-compose run --rm app make console

compose-restart:
	docker-compose restart

compose-stop:
	docker-compose stop || true

compose-setup: compose-down compose-build compose-install

compose-test:
	docker-compose run --rm app make test

compose-ci-check:
	docker-compose run --rm app make ci-setup-check
