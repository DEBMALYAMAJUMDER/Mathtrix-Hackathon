FROM maven:3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/Hackathon-Project-0.0.1-SNAPSHOT.jar Hackathon-Project.jar
EXPOSE 20000
ENTRYPOINT ["java","-jar","Hackathon-Project.jar"]