#!/usr/bin/env bash
# exit on error
set -o errexit

function load_dotenv_file() {
  current_dir="$(pwd)";
  script_dir="$(dirname $0)";
  cd "$script_dir"
  export DOTENV_FILE='/etc/secrets/.env'
  if [ -f "$DOTENV_FILE" ]; then export $(sed '/^\s*$/d; /^[[:space:]]*#/d' "$DOTENV_FILE"); fi
  cd $current_dir
}
