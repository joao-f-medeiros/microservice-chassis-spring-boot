#!/bin/ash

source /app/env.conf
export SPRING_PROFILE="${SPRING_PROFILE}"
export JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${SPRING_PROFILE}"

java ${JAVA_OPTS} -jar /app/application.jar