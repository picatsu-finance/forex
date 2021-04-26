#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-forex")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-forex
docker push ezzefiohez/finance-forex

echo " ######## BUILD FOREX DONE ######## "

curl  -X POST http://146.59.195.214:9000/api/webhooks/8e76da74-0bc1-4fc6-89eb-41f0d19488c1
