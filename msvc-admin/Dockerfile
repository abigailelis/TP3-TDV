FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn install -N
COPY src ./src
RUN mvn -fpom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

