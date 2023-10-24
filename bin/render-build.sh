#!/usr/bin/env bash
# exit on error
set -o errexit

source "$(dirname $0)/render_lib.sh" && load_dotenv_file

printf "$CREDENTIALS_ENC" > config/credentials.yml.enc
printf "$RAILS_MASTER_KEY" > config/master.key

bundle install
npm install
npm run build

bundle exec rake assets:precompile
bundle exec rake assets:clean

./bin/rails db:prepare
./bin/rails db:seed
