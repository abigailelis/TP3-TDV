FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn install -N
COPY msvc-admin/pom.xml ./msvc-admin/
COPY msvc-admin/src ./msvc-admin/src/
RUN mvn -f msvc-admin/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim
EXPOSE 8005
COPY --from=builder /app/msvc-admin/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

