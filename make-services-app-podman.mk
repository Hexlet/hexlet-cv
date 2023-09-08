podman-compose-app-bash:
	podman-compose run --rm app bash

podman-compose-app-install-npm:
	podman-compose run --rm app npm install --force

podman-compose-app-install-bundle:
	podman-compose run --rm app bundle install --jobs $(shell nproc)

podman-compose-app-update: podman-compose-app-update-bundle podman-compose-app-update-npm

podman-compose-app-update-bundle:
	podman-compose run --rm app bundle update --jobs $(shell nproc)

podman-compose-app-update-npm:
	podman-compose run --rm web npm upgrade

podman-compose-app-debug:
	podman attach --sig-proxy=false --detach-keys="ctrl-c" $(shell podman ps -q --latest --filter "name=hexlet-cv_app_*")

podman-compose-app-lint:
	podman-compose run --rm app make lint

podman-compose-app-lint-fix:
	podman-compose run --rm app make linter-code-fix

podman-compose-app-lint-eslint:
	podman-compose run --rm app make lint-eslint

podman-compose-app-lint-eslint-fix:
	podman-compose run --rm app make lint-eslint-fix

podman-compose-app-rails-console:
	podman-compose run --rm app make console

podman-compose-app-test:
	podman-compose run --rm app make test

podman-compose-app-ci-check:
	podman-compose run --rm app make ci-setup-check

podman-compose-app-rails:
	podman-compose run --rm app bin/rails $(T)

podman-compose-app-make:
	podman-compose run --rm app make $(T)
