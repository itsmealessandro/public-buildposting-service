#!/bin/bash
# 1) avvia i 3 servizi necessari per gli endpoint del progetto.
cd ./services
./run_services.sh &
$PID_SERVICES=$!
echo "PID_SERVICES: $PID_SERVICES"


sleep 5
cd ..

# 2) avvia camunda
cd ./camundaApi
mvn spring-boot:run