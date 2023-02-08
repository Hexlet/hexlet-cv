#!/bin/bash
curl -X POST https://sentry.io/api/0/organizations/hexlet-sq/releases/ \
     -H "Authorization: Bearer ${SENTRY_API_TOKEN}" \
     -H 'Content-Type: application/json' \
     -d "{\"environment\":\"${RAILS_ENV}\", \"version\":\"${SENTRY_RELEASE}\", \"projects\":[\"hexlet-cv\"]}"

curl -v -X POST https://sentry.io/api/0/organizations/hexlet-sq/releases/$SENTRY_RELEASE/deploys/ \
     -H "Authorization: Bearer ${SENTRY_API_TOKEN}" \
     -H 'Content-Type: application/json' \
     -d "{\"environment\":\"${RAILS_ENV}\"}"
