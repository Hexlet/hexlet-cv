FROM ruby:3.3.4

ENV NODE_VERSION 19.x

RUN curl -sL https://deb.nodesource.com/setup_${NODE_VERSION} | bash -

RUN wget http://archive.ubuntu.com/ubuntu/pool/main/o/openssl/libssl1.1_1.1.0g-2ubuntu4_amd64.deb && \
    dpkg -i libssl1.1_1.1.0g-2ubuntu4_amd64.deb

RUN apt-get update \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

ENV PROJECT_ROOT /app
RUN mkdir -p ${PROJECT_ROOT}

WORKDIR ${PROJECT_ROOT}

ENV BUNDLE_APP_CONFIG ${PROJECT_ROOT}/bundle/config
ENV GEM_HOME ${PROJECT_ROOT}/vendor/bundle
ENV BUNDLE_PATH ${GEM_HOME}
