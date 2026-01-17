#!/bin/bash

echo "1. Sending Booking Request..."
RESPONSE=$(curl -s -X POST http://localhost:8080/api/booking/request \
  -H "Content-Type: application/json" \
  -d '{
    "username": "mariorossi",
    "cities": ["Rome"],
    "format": "50x70",
    "algorithm": "greedy",
    "maxPrices": {"Rome": 50.0}
  }')

echo "Response: $RESPONSE"

REQUEST_ID=$(echo $RESPONSE | grep -o '"requestId":"[^"]*' | cut -d'"' -f4)
BUSINESS_KEY=$(echo $RESPONSE | grep -o '"businessKey":"[^"]*' | cut -d'"' -f4)

echo "Request ID: $REQUEST_ID"
echo "Business Key: $BUSINESS_KEY"

if [ -z "$REQUEST_ID" ] || [ -z "$BUSINESS_KEY" ]; then
  echo "Failed to get Request ID or Business Key"
  exit 1
fi

echo "2. Checking History (Pending)..."
HISTORY_PENDING=$(curl -s http://localhost:8080/api/booking/history)
echo "History Pending: $HISTORY_PENDING"

echo "3. Sending Cancel Decision..."
DECISION_RESPONSE=$(curl -s -X POST http://localhost:8080/api/booking/decision \
  -H "Content-Type: application/json" \
  -d "{
    \"requestId\": \"$REQUEST_ID\",
    \"decision\": \"CANCEL\",
    \"businessKey\": \"$BUSINESS_KEY\"
  }")

echo "Decision Response: $DECISION_RESPONSE"

echo "3. Checking History..."
HISTORY=$(curl -s http://localhost:8080/api/booking/history)
echo "History: $HISTORY"
