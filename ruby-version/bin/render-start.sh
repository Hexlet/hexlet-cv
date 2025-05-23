#!/usr/bin/env bash
# exit on error
set -o errexit

source "$(dirname $0)/render_lib.sh" && load_dotenv_file

bundle exec puma -C config/puma.rb
