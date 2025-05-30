#!/bin/sh
DEFAULT_JVM_OPTS=""
exec java $DEFAULT_JVM_OPTS -jar gradle/wrapper/gradle-wrapper.jar "$@"
