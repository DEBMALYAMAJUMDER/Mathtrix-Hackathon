FROM maven:3-openjdk-17 AS build
WORKDIR /app
# copy pom first for dependency caching
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM openjdk:17-slim
WORKDIR /app
# use a wildcard so we don't hardcode the built artifact version
COPY --from=build /app/target/*-SNAPSHOT.jar ./app.jar
EXPOSE 20000
ENTRYPOINT ["java", "-jar", "./app.jar"]