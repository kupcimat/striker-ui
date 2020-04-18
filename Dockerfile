# Install javascript dependencies
FROM node:slim AS yarn-build
WORKDIR /tmp/build

COPY package.json .
COPY yarn.lock .
RUN yarn install

# Install clojure dependencies
FROM clojure:openjdk-14-lein AS lein-build
WORKDIR /tmp/build

COPY project.clj .
RUN lein deps

# Build application
COPY --from=yarn-build /tmp/build .
COPY shadow-cljs.edn .
COPY .git .git
COPY resources resources
COPY src src
RUN lein prod

# Build production image
FROM nginx:alpine

COPY config/nginx-config.template /etc/nginx/conf.d/nginx-config.template
COPY --from=lein-build /tmp/build/resources/public /usr/share/nginx/html

CMD /bin/sh -c "envsubst '\$PORT \$BACKEND_URI' < /etc/nginx/conf.d/nginx-config.template > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"
