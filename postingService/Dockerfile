FROM openjdk:8-jre-alpine

WORKDIR /app

COPY posting-service.jar /app/posting-service.jar

EXPOSE 9080

CMD ["java", "-jar", "posting-service.jar"]