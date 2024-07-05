###TEST GRADLE EMBEBIDO
# Multistage dockerfile para hacer build con gradle

# Build stage
FROM gradle:latest AS builder

# Crear directorio de la aplicación
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar archivos de Gradle Wrapper y configuraciones de build
COPY gradlew $APP_HOME/
COPY gradle $APP_HOME/gradle
COPY build.gradle settings.gradle $APP_HOME/

# Copiar el resto del proyecto
COPY . .

# Dar permisos de ejecución al script gradlew
RUN chmod +x gradlew

# Ejecutar el build usando Gradle Wrapper
RUN ./gradlew build

# Run stage
FROM openjdk:17-alpine AS runner

ENV ARTIFACT_NAME=expsys.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar el JAR generado desde el build stage
COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME

# Definir el comando de entrada para ejecutar la aplicación
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
#ENTRYPOINT exec java -jar -Dspring.profiles.active=dev ${ARTIFACT_NAME}