podman-compose:
	podman-compose up -d

podman-compose-build:
	podman-compose build

podman-compose-clear:
	podman-compose down -v --remove-orphans || true

podman-compose-down:
	podman-compose down || true

podman-compose-install:
	podman-compose run --rm app make setup

podman-compose-logs:
	podman-compose logs -f

podman-compose-restart:
	podman-compose restart

podman-compose-stop:
	podman-compose stop || true

compose-setup: podman-compose-down podman-compose-build podman-compose-install
