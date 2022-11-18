FROM openjdk:17-jdk-alpine
ARG JAR_FILE=backend/target/backend-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /rockpaperscissors.jar
ENTRYPOINT [ "java", "-jar", "/rockpaperscissors.jar" ]
