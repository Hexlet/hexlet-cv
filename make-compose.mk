compose:
	docker-compose up -d

compose-build:
	docker-compose build

compose-clear:
	docker-compose down -v --remove-orphans || true

compose-down:
	docker-compose down || true

compose-install:
	docker-compose run --rm app make setup

compose-logs:
	docker-compose logs -f

compose-restart:
	docker-compose restart

compose-stop:
	docker-compose stop || true

compose-setup: compose-down compose-build compose-install
