#!/bin/bash

# Assicura che i processi figli vengano terminati al termine dello script
cleanup() {
  echo "Shutting down services..."
  kill $POSTING_PID $USER_PID $ZONES_PID 2>/dev/null
  wait
  echo "All services stopped."
  exit 0
}

# Registra la funzione cleanup per segnali di interruzione
trap cleanup SIGINT SIGTERM

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

echo "Services are running. Press Ctrl+C to stop."

# Aspetta che tutti i processi figli finiscano (o uno di essi)
wait
