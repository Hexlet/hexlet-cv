web: bundle exec puma -t 5:5 -p ${PORT:-3000} -e ${RACK_ENV:-development}
frontend: make frontend
release: sh -c 'bin/rake db:migrate && ./deploy-notify.sh'
