# Construcción
FROM gradle:8.4.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --stacktrace -x test

# Ejecución
FROM eclipse-temurin:21-jre
RUN mkdir /app

EXPOSE ${APP_PORT}

COPY --from=build /home/gradle/src/build/libs/*.jar /app/products.jar

ENTRYPOINT ["java", "-Dserver.port=${APP_PORT}", "-Dorg.gradle.daemon=false", "-jar", "/app/products.jar"]