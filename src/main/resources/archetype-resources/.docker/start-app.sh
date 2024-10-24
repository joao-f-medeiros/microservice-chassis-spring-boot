#!/bin/ash

source /app/env.conf
export SPRING_PROFILE="${SPRING_PROFILE}"
export JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${SPRING_PROFILE}"
export DD_AGENT_ENABLED="${DD_AGENT_ENABLED}"
if [ "$DD_AGENT_ENABLED" = "true" ]; then
    export ENVIRONMENT="${ENVIRONMENT}"
    export DD_AGENT_URL="-datadog-agent.monitoring.svc.cluster.local"
    JAVA_OPTS="$JAVA_OPTS -Ddd.agent.host=${DD_AGENT_URL} -Ddd.env=${ENVIRONMENT} -Ddd.service=${APP_NAME}
                          -Ddd.profiling.enabled=true -Ddd.profiling.allocation.enabled=true -Ddd.data.streams.enabled=true"
fi

java "${JAVA_OPTS}" -jar /app/application.jar