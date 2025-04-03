#!/bin/bash

# Step 1: Log in to get JWT
echo "Logging in to https://jjldomain.dk..."
response=$(curl -s -X POST https://jjldomain.dk/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password"}')

# Step 2: Extract token
token=$(echo $response | grep -o '"token":"[^"]*' | grep -o '[^"]*$')

if [ -z "$token" ]; then
  echo "❌ Failed to retrieve JWT. Response:"
  echo $response
  exit 1
fi

echo "✅ JWT retrieved."

# Step 3: Fetch protected data with token
echo "Fetching meals using token..."
curl -s -H "Authorization: Bearer $token" https://jjldomain.dk/api/meals -o meals.json

# Step 4: Open JSON in browser (Windows)
echo "Opening response in browser..."
start "" "meals.json"
