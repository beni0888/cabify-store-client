FROM openjdk:8-jdk-alpine
LABEL maintainer="beni0888@hotmail.com" maintainer="jesus.benito.c@gmail.com"
ADD cabify-store-client-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]