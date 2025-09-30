FROM openjdk:8-jre-alpine

WORKDIR /app

COPY user-service.jar /app/user-service.jar

EXPOSE 9080

CMD ["java", "-jar", "user-service.jar"]