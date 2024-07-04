# Build stage
FROM gradle:8.7-jdk AS builder

# Crear directorio de la aplicación
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar archivos de Gradle Wrapper y configuraciones de build
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

# Copiar el resto del proyecto
COPY . .

# Ejecutar el build usando Gradle Wrapper
RUN gradle clean build

# Run stage
FROM openjdk:17-alpine AS runner

ENV ARTIFACT_NAME=expsys-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar el JAR generado desde el build stage
COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME
EXPOSE 8080

# Definir el comando de entrada para ejecutar la aplicación
#CMD ["java","-jar","test-gradle.jar"]
ENTRYPOINT exec java -jar -Dspring.profiles.active=dev ${ARTIFACT_NAME}