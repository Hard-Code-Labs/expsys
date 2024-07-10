#FROM gradle:jdk17 AS builder
#
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#
#COPY . .
#RUN chmod +x gradlew
#RUN gradle clean build --no-daemon

#FROM bellsoft/liberica-openjdk-alpine-musl
#
#ENV ARTIFACT_NAME=expsys.jar
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#
##COPY --from=builder $APP_HOME/build/libs/*.jar $ARTIFACT_NAME
#COPY build/libs/expsys-0.0.1-SNAPSHOT.jar $ARTIFACT_NAME
#EXPOSE 8080
##ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT exec java -jar ${ARTIFACT_NAME}

# Usar la imagen oficial de Gradle para construir la aplicación
FROM gradle:jdk17 AS builder

# Crear directorio de la aplicación
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Copiar el proyecto
COPY . .

# Construir la aplicación
RUN gradle clean build --no-daemon

# Usar una imagen base ligera de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Crear directorio de la aplicación en la imagen final
WORKDIR /app

# Copiar el JAR construido desde la etapa de construcción
COPY --from=builder /usr/app/build/libs/*.jar app.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

