FROM ruby:3.2.0

WORKDIR /usr/src/app/

RUN apt update && apt install -y npm curl && \
    curl -fsSL https://deb.nodesource.com/setup_19.x | bash - && \
    apt install -y nodejs && \
    npm install -g npm@latest yarn&& \
    npm cache clean --force && \
    rm -rf /var/lib/apt/lists/*

COPY . .

RUN make setup

CMD "bundle exec puma -t 5:5 -p ${PORT:-3000} -e ${RACK_ENV:-development}"