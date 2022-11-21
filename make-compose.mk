compose:
	docker-compose up -d

compose-build:
	docker-compose build

compose-logs:
	docker-compose logs -f

compose-down:
	docker-compose down || true

compose-clear:
	docker-compose down -v --remove-orphans || true

compose-stop:
	docker-compose stop || true

compose-restart:
	docker-compose restart

compose-test:
	docker-compose run --rm app make fixtures-load && make test

compose-lint:
	docker-compose run --rm app make lint

compose-lint-fix:
	docker-compose run --rm app make linter-code-fix

