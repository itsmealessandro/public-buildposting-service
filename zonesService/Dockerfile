FROM openjdk:8-jre-alpine

WORKDIR /app

COPY zones-service.jar /app/zones-service.jar

EXPOSE 9090

CMD ["java", "-jar", "zones-service.jar"]