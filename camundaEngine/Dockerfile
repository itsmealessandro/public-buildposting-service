FROM openjdk:8-jdk-alpine

WORKDIR /app

RUN apk add --no-cache maven

# Copia solo i file Maven necessari per la cache delle dipendenze
COPY camundaApi/pom.xml /app/camundaApi/pom.xml

WORKDIR /app/camundaApi

RUN mvn dependency:go-offline

# Ora copia tutto il resto del progetto
COPY . /app

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]