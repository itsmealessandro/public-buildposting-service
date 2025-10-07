FROM maven:3-jdk-8

WORKDIR /app

# Copia solo i file Maven necessari per la cache delle dipendenze
COPY camundaApi/pom.xml /app/camundaApi/pom.xml

WORKDIR /app/camundaApi

# runna solo se pom.xml cambia e la prima volta che si scarica l'immagine
RUN mvn dependency:go-offline

# Ora copia tutto il resto del progetto
COPY . /app

EXPOSE 8080

RUN chmod +x /app/entrypoint.sh 

# tra i 10 e i 20 secondi per avviare l'app
CMD ["/app/entrypoint.sh"]