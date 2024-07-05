# Run stage
FROM openjdk:17-alpine AS runner

ENV ARTIFACT_NAME=expsys.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Ejecutar el build usando Gradle Wrapper
RUN ./gradlew build --no-daemon

# Copiar el JAR generado desde el build stage
COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME

# Definir el comando de entrada para ejecutar la aplicación
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
#ENTRYPOINT exec java -jar -Dspring.profiles.active=dev ${ARTIFACT_NAME}