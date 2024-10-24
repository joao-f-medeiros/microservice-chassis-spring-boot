FROM azul/zulu-openjdk-alpine:17-jre-latest
LABEL org.opencontainers.image.authors="Projuris-Ops <blackops@projuris.com.br>"

ARG USER_NAME="app"
ARG USER_UID="1000"
ARG GROUP_NAME="app"
ARG GROUP_GID="1000"
ARG USER_HOME="/app/"

# Create a user and group used to launch processes
RUN addgroup -g "$GROUP_GID" -S "$GROUP_NAME" && \
    adduser -u "$USER_UID" -D -S -h "$USER_HOME" -s /sbin/nologin -G "$GROUP_NAME" "$USER_NAME"

# Create app diretory and set permissions
RUN set -ex && \
    chown -R "$USER_UID":"$GROUP_GID" "$USER_HOME"/ && \
    find "$USER_HOME"/ -type f -exec chmod 664 {} \; && \
    find "$USER_HOME"/ -type d -exec chmod 775 {} \;

RUN set -ex && \
    wget -q -O "$USER_HOME"/dd-java-agent.jar "https://dtdg.co/latest-java-tracer"

RUN stat /app/dd-java-agent.jar > /dev/null 2>&1 || (echo "Arquivo dd-java-agent.jar não encontrado em /app"; exit 1)


COPY --chown=$USER_NAME:$GROUP_NAME ./.docker/env.conf /app/env.conf
COPY --chown=$USER_NAME:$GROUP_NAME ./.docker/start-app.sh /app/start-app.sh
COPY --chown=$USER_NAME:$GROUP_NAME ./target/*.jar /app/application.jar

WORKDIR $USER_HOME
USER $USER_NAME

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["sh","/app/start-app.sh"]
