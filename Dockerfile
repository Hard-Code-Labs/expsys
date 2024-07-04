FROM gradle:latest AS builder

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build || return 0
COPY . .
RUN gradle clean build

FROM bellsoft/liberica-openjdk-alpine-musl

ENV ARTIFACT_NAME=expsys-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME
EXPOSE 8080
#CMD ["java","-jar","test-gradle.jar"]
ENTRYPOINT exec java -jar -Dspring.profiles.active=dev ${ARTIFACT_NAME}