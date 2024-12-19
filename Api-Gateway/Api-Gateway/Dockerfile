FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/Api-Gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV EUREKA_SERVER_URI=http://localhost:8761/eureka/
ENTRYPOINT ["java", "-jar", "/app.jar", "--eureka.client.serviceUrl.defaultZone=${EUREKA_SERVER_URI}"]
EXPOSE 8015
