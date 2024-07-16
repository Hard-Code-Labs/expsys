FROM bellsoft/liberica-openjdk-alpine-musl
#FROM openjdk:17-jdk-slim

ENV ARTIFACT_NAME=expsys.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY /build/libs/expsys-0.0.1-SNAPSHOT.jar $ARTIFACT_NAME
EXPOSE 8080

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}