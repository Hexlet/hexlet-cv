# Hexlet CV

[![Maintainability](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/maintainability)](https://codeclimate.com/github/Hexlet/hexlet-cv/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/test_coverage)](https://codeclimate.com/github/Hexlet/hexlet-cv/test_coverage)
[![github action status](https://github.com/Hexlet/hexlet-cv/workflows/Main%20workflow/badge.svg)](https://actions-badge.atrox.dev/hexlet/hexlet-cv/goto)

## System requirements

* Ruby
* Node.js < 17.0.0
* Yarn
* SQLite3
* [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)

## Setup

```sh
make setup
make test # run tests
make start # run server http://localhost:3000

make fixtures-load # sometimes, when fixtures were changed
```

## Deploy

Add database:

```sh
heroku addons:create heroku-postgresql:hobby-dev
```

Prepare environment variables:

```sh
heroku config:set SECRET_KEY_BASE=$(rake secret)
heroku config:set HOST=$(heroku info -s | grep web_url | cut -d= -f2) # https://cv.hexlet.io for production
heroku config:set RACK_ENV=production
heroku config:set RAILS_ENV=production
heroku config:set RAILS_LOG_TO_STDOUT=enabled
heroku config:set EMAIL_FROM=support@hexlet.io
```

Configure reCAPTCHA

* Follow the link [reCAPTCHA](https://www.google.com/recaptcha)
* Log into Admin Console with your credentials or create a new Google Account in case you don't have one
* At Admin Console register a new site as shown in example below

```sh
Label: <app_name>.herokuapp.com
Type reCAPTCHA: reCAPTCHA v2 (Checkbox "I'm not a robot")
Domains: <app_name>.herokuapp.com (localhost or/and 0.0.0.0 for development env)
```
* Accept terms of use and submit
* Add generated reCAPTCHA `SITE KEY` and `SECRET KEY` to environment variables in production
* To use reCAPTCHA in development simply copy `SITE KEY` and `SECRET KEY` to your .env file
---

[![Hexlet Ltd. logo](https://raw.githubusercontent.com/Hexlet/assets/master/images/hexlet_logo128.png)](https://hexlet.io/pages/about?utm_source=github&utm_medium=link&utm_campaign=hexlet-cv)

This repository is created and maintained by the team and the community of Hexlet, an educational project. [Read more about Hexlet](https://hexlet.io/pages/about?utm_source=github&utm_medium=link&utm_campaign=hexlet-cv).

See most active contributors on [hexlet-friends](https://friends.hexlet.io/).
