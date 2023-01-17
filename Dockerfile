FROM ruby:3.2.0

ENV NODE_VERSION 19.x

RUN curl -sL https://deb.nodesource.com/setup_${NODE_VERSION} | bash -

RUN apt-get update \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app/

COPY . .

<<<<<<< HEAD
# RUN apt update && apt install -y nodejs npm

# RUN bin/rake db:migrate
=======
RUN make setup

RUN bin/rake db:migrate
>>>>>>> update docker file

CMD "bundle exec puma -t 5:5 -p ${PORT:-3000} -e ${RACK_ENV:-development}"
