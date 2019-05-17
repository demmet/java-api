echo "Starting Ngrok and API"
./src/ngrok/ngrok http 8080 &
java -jar build/libs/java-api-0.0.1-SNAPSHOT.jar &
echo "Ngrok and API started"