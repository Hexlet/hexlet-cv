FROM ruby:3.2.0

WORKDIR /usr/src/app/

RUN apt update && apt install -y npm curl && \
    curl -fsSL https://deb.nodesource.com/setup_19.x | bash - && \
    apt install -y nodejs && \
    npm install npm@latest && \
    npm install --global yarn && npm cache clean --force 

COPY . .

RUN make setup

CMD "bundle exec puma -t 5:5 -p ${PORT:-3000} -e ${RACK_ENV:-development}"