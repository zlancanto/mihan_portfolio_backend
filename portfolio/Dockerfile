# Étape 1 : Build avec Gradle 9 (pour supporter Spring Boot 4)
FROM gradle:9.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# On construit le JAR
RUN gradle build -x test --no-daemon

# Étape 2 : Exécution avec Java 21 stable
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# On récupère le fichier JAR généré
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
# Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]