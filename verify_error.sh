#!/bin/bash

echo "1. Sending Malformed Request (Should return 400 or 500 with JSON)..."
# Sending invalid JSON (missing closing brace)
RESPONSE=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X POST http://localhost:8080/api/booking/request \
  -H "Content-Type: application/json" \
  -d '{
    "username": "mariorossi",
    "cities": ["Rome"]
  ')

echo "Response: $RESPONSE"

echo "2. Sending Request with Missing Data (Should trigger NullPointer or similar)..."
# Sending empty body which might cause issues if not handled
RESPONSE_EMPTY=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X POST http://localhost:8080/api/booking/request \
  -H "Content-Type: application/json" \
  -d '{}')

echo "Response Empty: $RESPONSE_EMPTY"
