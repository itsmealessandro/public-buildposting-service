FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY . /app

EXPOSE 8080

RUN apk add --no-cache maven

WORKDIR /app/camundaApi

CMD ["mvn", "spring-boot:run"]