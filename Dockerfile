FROM maven:3.1.9-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:11
WORKDIR /app
COPY --from=build app/target/EspritGather-0.0.1-SNAPSHOT.jar EspritGather-0.0.1.jar
ENTRYPOINT ["java","-jar","/app/EspritGather-0.0.1.jar"]
