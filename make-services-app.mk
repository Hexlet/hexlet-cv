app-bash:
	docker-compose run --rm app bash

app-install-npm:
	docker-compose run --rm app npm install --force

app-install-bundle:
	docker-compose run --rm app bundle install --jobs $(shell nproc)

app-debug:
	docker attach --sig-proxy=false --detach-keys="ctrl-c" $(shell docker ps -q --filter publish=3000)

app-lint:
	docker-compose run --rm app make lint

app-lint-fix:
	docker-compose run --rm app make linter-code-fix

app-lint-eslint:
	docker-compose run --rm app make lint-eslint

app-lint-eslint-fix:
	docker-compose run --rm app make lint-eslint-fix

app-rails-console:
	docker-compose run --rm app make console

app-test:
	docker-compose run --rm app make test

app-ci-check:
	docker-compose run --rm app make ci-setup-check

app-rails:
	docker-compose run --rm app bin/rails $(T)

app-make:
	docker-compose run --rm app make $(T)
