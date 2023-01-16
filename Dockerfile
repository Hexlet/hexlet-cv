FROM ruby:3.2.0

RUN apt-get update

ENV NODE_VERSION 19.x

RUN curl -sL https://deb.nodesource.com/setup_${NODE_VERSION} | bash -

RUN apt-get install -y nodejs

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
