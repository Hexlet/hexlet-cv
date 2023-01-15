FROM ruby:3.2.0

RUN apt update

RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN apt-get update \
    && apt-get install -y curl \
    && apt-get -y autoclean

RUN mkdir /usr/local/nvm
ENV NVM_DIR /usr/local/nvm
ENV NODE_VERSION 18.13.0

RUN curl --silent -o- https://raw.githubusercontent.com/creationix/nvm/v0.39.3/install.sh | bash

RUN source $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default

ENV NODE_PATH $NVM_DIR/v$NODE_VERSION/lib/node_modules
ENV PATH $NVM_DIR/versions/node/v$NODE_VERSION/bin:$PATH

# confirm installation
RUN node -v
RUN npm -v

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

