# Etapa 1: Build da aplicação
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /movie-awards-api

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon || true

COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Etapa 2: Imagem final mínima com distroless
FROM gcr.io/distroless/java21-debian12
WORKDIR /movie-awards-api
COPY --from=builder /movie-awards-api/build/libs/*.jar movie-awards-api.jar

ENTRYPOINT ["java", "-jar", "movie-awards-api.jar"]
