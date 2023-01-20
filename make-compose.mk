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

compose-lint:
	docker-compose run --rm app make lint

compose-lint-fix:
	docker-compose run --rm app make linter-code-fix

compose-logs:
	docker-compose logs -f

compose-restart:
	docker-compose restart

compose-stop:
	docker-compose stop || true

compose-setup: compose-down compose-build compose-install

compose-test:
	docker-compose run --rm app make test
