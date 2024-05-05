FROM eclipse-temurin:21-jdk-alpine AS build
LABEL authors="Emirhan Ese"

WORKDIR /app

COPY pom.xml /app/pom.xml
COPY mvnw /app/mvnw
COPY .mvn /app/.mvn

COPY src /app/src

RUN apk update && apk add dos2unix
RUN dos2unix ./mvnw
RUN --mount=type=cache,id=maven,target=/root/.m2 ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/pawbuddies.jar pawbuddies.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "pawbuddies.jar"]