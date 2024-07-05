###TEST GRADLE EMBEBIDO
# Multistage dockerfile para hacer build con gradle

FROM gradle:jdk17 AS builder

# Crear directorio de la aplicación
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar los archivos de Gradle y el wrapper primero (aprovechando la cache de Docker)
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle $APP_HOME/gradle

# Dar permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Descargar las dependencias (cache de Docker)
RUN ./gradlew --no-daemon build || return 0

# Copiar el resto del proyecto
COPY . .

# Ejecutar el build usando Gradle Wrapper
RUN ./gradlew clean build --no-daemon



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