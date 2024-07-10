#FROM gradle:jdk17 AS builder
#
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#
#COPY . .
#RUN chmod +x gradlew
#RUN gradle clean build --no-daemon

FROM bellsoft/liberica-openjdk-alpine-musl

ENV ARTIFACT_NAME=expsys.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

#COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME
COPY build/libs/expsys-0.0.1-SNAPSHOT.jar $ARTIFACT_NAME
EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}