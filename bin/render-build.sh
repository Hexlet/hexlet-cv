#!/usr/bin/env bash
# exit on error
set -o errexit

source "$(dirname $0)/render_lib.sh" && load_dotenv_file

test -n $CREDENTIALS_CLEAR && test -f config/credentials.yml.enc && rm config/credentials.yml.enc && echo CREDENTIALS_CLEAR
echo "$RAILS_MASTER_KEY" > config/master.key

bundle install
npm install
npm run build

bundle exec rake assets:precompile
bundle exec rake assets:clean

./bin/rails db:prepare
./bin/rails db:seed
