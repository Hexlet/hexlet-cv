# Hexlet CV

[![Maintainability](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/maintainability)](https://codeclimate.com/github/Hexlet/hexlet-cv/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/ac489ba3a4c73baf89a9/test_coverage)](https://codeclimate.com/github/Hexlet/hexlet-cv/test_coverage)
[![Main](https://github.com/Hexlet/hexlet-cv/actions/workflows/main.yml/badge.svg?branch=main&event=push)](https://github.com/Hexlet/hexlet-cv/actions/workflows/main.yml)

## About
The purpose of Hexlet CV is to provide a community platform where you get resume recommendations from community members and professional HR.

Interactions on Hexlet CV are based on resumes and resume recommendations. On a particular resume, each community member provides only one recommendation.

Join the community, post resumes and leave recommendations for other members. Tell your colleagues and friends about the site!

The project uses Ruby on Rails.

Tasks can be discussed in the [Telegram community](https://t.me/hexletcommunity/12).

## System requirements

* Ruby >= 3.2.2
* Node.js >= 19.0.0
* SQLite3
* [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)

## Setup

```sh
make setup

# or for Apple silicon machine if setup fails with unsupported arch

make setup-arm64

make test # run tests
make start # run server http://localhost:3000

make fixtures-load # sometimes, when fixtures were changed
```

## Setup in Docker

```sh
make compose-setup # setup app
make app-test # run tests
make compose # run server http://localhost:3000

make app-ci-check # run ci
```

## Debug in Docker

```sh
docker container ls # watch container
docker attach [container_id]

or

make app-debug
```

and insert `debugger` in controller

## Setup in Podman

Dependencies: podman, podman-compose

If you have installed podman-docker, you can use, directly, `make compose-*` commands (except `make app-debug`)

Or explicitly use the `make podman-compose-*` commands.

```sh
make podman-compose-setup # setup app
make podman-compose-app-test # run tests
make podman-compose # run server http://localhost:3000

make podman-compose-app-ci-check
```

## Debug in Podman

```sh
podman container ls # watch container
podman attach [container_id]

or

make podman-compose-app-debug

```

and insert `debugger` in controller

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

## Deploy to render.com
<details><summary>INFO IS HERE</summary>

* Go to https://dashboard.render.com
* Add New PostgreSQL with YOUR_CUSTOM_NAME_PG and select Region
* Add New Web Service with link to your repo clone\
  select:
    * YOUR_CUSTOM_NAME_CV
    * same Region
    * Runtime: Ruby
    * Build Command - `./bin/render-build.sh`
    * Start Command - `./bin/render-start.sh` or `bundle exec puma -C config/puma.rb`
* Go to YOUR_CUSTOM_NAME_PG PostgreSQL -> Info and copy `Internal Database URL`

* Go to YOUR_CUSTOM_NAME_CV app -> Environment
    * Environment Variables, by one\
      or
    * Secret Files .env with your settings, based on .env.example and add this variables:
      * HOST
        ```shell
        echo "HOST=your-app-name.onrender.com" >> .env
        ```
      * EMAIL_SPECIAL_USER
        ```shell
        echo "EMAIL_SPECIAL_USER=any_existing_email@in_database" >> .env
        ```
      * DATABASE_URL
        ```shell
        echo "DATABASE_URL=Internal Database URL" >> .env
        ```
      * RACK_ENV and RAILS_ENV
        ```shell
        echo "RACK_ENV=staging" >> .env
        echo "RAILS_ENV=staging" >> .env
        ```
      * RENDER_LOAD_FIXTURES to load fixtures
        ```shell
        echo "RENDER_LOAD_FIXTURES=1" >> .env
        ```
      Generate new master.key if the original is missing
      * RAILS_MASTER_KEY
        ```shell
        export RAILS_MASTER_KEY="$(ruby -r securerandom -e 'print SecureRandom.hex(16)')"
        echo $RAILS_MASTER_KEY
        printf $RAILS_MASTER_KEY > config/master.key
        echo "RAILS_MASTER_KEY=$RAILS_MASTER_KEY" >> .env
        rm config/credentials.yml.enc
        EDITOR=vim bin/rails credentials:edit # to update config/credentials.yml.enc
                                              # press :wq+Enter
        ```
      * CREDENTIALS_ENC
        ```shell
        echo "CREDENTIALS_ENC=$(cat config/credentials.yml.enc)" >> .env
        ```

* You can deploy app
</details>

---

Configure reCAPTCHA for production:

* Follow the link [reCAPTCHA](https://www.google.com/recaptcha)
* Log into Admin Console with your credentials or create a new Google Account in case you don't have one
* At Admin Console register a new site as shown in example below

Configure reCAPTCHA for development:

* add test key to .ENV file if they were not generated:

```
# test key for recapcha https://github.com/MTG/freesound/issues/879
RECAPTCHA_SITE_KEY=6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI
RECAPTCHA_SECRET_KEY=6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe
```

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
