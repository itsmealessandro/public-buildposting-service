FROM maven:3-jdk-8

WORKDIR /app

# Copia solo i file Maven necessari per la cache delle dipendenze
COPY camundaApi/pom.xml /app/camundaApi/pom.xml

WORKDIR /app/camundaApi

RUN mvn verify

# Ora copia tutto il resto del progetto
COPY . /app

EXPOSE 8080

RUN chmod +x /app/entrypoint.sh 

CMD ["/app/entrypoint.sh"]