# --- FASE 1: Compilazione del progetto con Maven (Java 21) ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- FASE 2: Esecuzione dell'applicazione (Java 21) ---
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]