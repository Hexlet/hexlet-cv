
compose:
	docker-compose up -d 

compose-setup: compose-build
	docker-compose run app bin/setup
	docker-compose run app yarn

compose-stop:
	docker-compose stop

compose-kill:
	docker-compose kill

compose-down:
	docker-compose down || true

compose-restart:
	docker-compose restart

compose-logs:
	docker-compose logs -f --tail=100

compose-build:
	docker-compose build
