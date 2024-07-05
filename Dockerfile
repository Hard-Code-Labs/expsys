FROM gradle:jdk17 AS builder

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build --no-daemon

FROM openjdk:17-jdk-slim

ENV ARTIFACT_NAME=expsys.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT exec java -jar -Dspring.profiles.active=dev ${ARTIFACT_NAME}