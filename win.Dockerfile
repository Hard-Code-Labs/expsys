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

