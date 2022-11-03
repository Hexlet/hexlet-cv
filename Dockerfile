FROM ruby:3.1.2

WORKDIR /usr/src/app/

COPY . .

RUN apt update && apt install -y nodejs npm && npm install --global yarn

RUN make setup

RUN bin/rake db:migrate

CMD "bundle exec puma -t 5:5 -p ${PORT:-3000} -e ${RACK_ENV:-development}"

