FROM ruby:3.2.2

ENV NODE_VERSION 19.x

RUN curl -sL https://deb.nodesource.com/setup_${NODE_VERSION} | bash -

RUN apt-get update \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

ENV PROJECT_ROOT /app
RUN mkdir -p ${PROJECT_ROOT}

WORKDIR ${PROJECT_ROOT}

ENV BUNDLE_APP_CONFIG ${PROJECT_ROOT}/bundle/config
ENV GEM_HOME ${PROJECT_ROOT}/vendor/bundle
ENV BUNDLE_PATH ${GEM_HOME}
