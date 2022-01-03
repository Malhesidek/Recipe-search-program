FROM openjdk:17-alpine

WORKDIR opt/server1
COPY ./target/recipe-service-0.0.1-SNAPSHOT.jar server1.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "server1.jar"]