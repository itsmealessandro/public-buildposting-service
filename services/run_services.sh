#!/bin/bash

# Porte richieste dai servizi
PORTS=(9090 9080 8888)
SERVICE_NAMES=("posting-service.jar" "user-service.jar" "zones-service.jar")

# Funzione per controllare se una porta è occupata
check_ports() {
  for port in "${PORTS[@]}"; do
    if lsof -i ":$port" -sTCP:LISTEN >/dev/null 2>&1; then
      echo "❌ Port $port is already in use. Please free it before starting."
      exit 1
    else
      echo "✅ Port $port is available."
    fi
  done
}

# Funzione di cleanup per terminare i servizi
cleanup() {
  echo "Shutting down services..."
  kill $POSTING_PID $USER_PID $ZONES_PID 2>/dev/null
  wait
  echo "All services stopped."
  exit 0
}

# Registra la funzione cleanup per segnali di interruzione
trap cleanup SIGINT SIGTERM

# 1️⃣ Controllo porte
echo "Checking ports..."
check_ports
echo "All required ports are free."

# 2️⃣ Avvio dei servizi
echo "Starting services..."

echo "POSTING ..."
java -jar ./posting-service.jar &
POSTING_PID=$!
echo "POSTING PID: ${POSTING_PID}"

echo "USER ..."
java -jar ./user-service.jar &
USER_PID=$!
echo "USER PID: ${USER_PID}"

echo "ZONES ..."
java -jar ./zones-service.jar &
ZONES_PID=$!
echo "ZONES PID: ${ZONES_PID}"

echo "✅ Services are running. Press Ctrl+C to stop."

# 3️⃣ Attesa processi
wait
