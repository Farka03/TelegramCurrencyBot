FROM openjdk:17
ADD target/bot-api-docker.jar bot-api-docker.jar
ENTRYPOINT ["java", "-jar","bot-api-docker.jar"]
EXPOSE 8080