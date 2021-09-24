# Hexlet CV

[![Maintainability](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/maintainability)](https://codeclimate.com/github/Hexlet/hexlet-cv/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/test_coverage)](https://codeclimate.com/github/Hexlet/hexlet-cv/test_coverage)
[![github action status](https://github.com/Hexlet/hexlet-cv/workflows/Main%20workflow/badge.svg)](https://actions-badge.atrox.dev/hexlet/hexlet-cv/goto)

## Участие

* Обсуждение в канале [#hexlet-volunteers](https://slack-ru.hexlet.io) слака

## Системные требования

* Ruby
* Node.js
* Yarn
* SQLite3
* [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)

## Установка

```sh
make setup
make test # run tests
make start # run server

make fixtures-load # sometimes, when fixtures were changed
```

## Deploy

Добавить базу данных

```sh
heroku addons:create heroku-postgresql:hobby-dev
```

Подготовить необходимые переменные окружения:

```sh
heroku config:set SECRET_KEY_BASE=$(rake secret)
heroku config:set HOST=$(heroku info -s | grep web_url | cut -d= -f2) # https://cv.hexlet.io for production
heroku config:set RACK_ENV=production
heroku config:set RAILS_ENV=production
heroku config:set RAILS_LOG_TO_STDOUT=enabled
heroku config:set EMAIL_FROM=support@hexlet.io
```

---

[![Hexlet Ltd. logo](https://raw.githubusercontent.com/Hexlet/assets/master/images/hexlet_logo128.png)](https://ru.hexlet.io/pages/about?utm_source=github&utm_medium=link&utm_campaign=exercises-javascript)

This repository is created and maintained by the team and the community of Hexlet, an educational project. [Read more about Hexlet (in Russian)](https://ru.hexlet.io/pages/about?utm_source=github&utm_medium=link&utm_campaign=exercises-javascript).
