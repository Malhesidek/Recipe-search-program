FROM openjdk:17-alpine

WORKDIR opt/server2
COPY ./target/subject-service-0.0.1-SNAPSHOT.jar server2.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "server2.jar"]