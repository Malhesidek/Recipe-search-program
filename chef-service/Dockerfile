FROM openjdk:17-alpine

WORKDIR opt/server3
COPY ./target/chef-service-0.0.1-SNAPSHOT.jar server3.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "server3.jar"]