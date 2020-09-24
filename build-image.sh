#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-forex")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-forex
docker push ezzefiohez/finance-forex

echo " ######## BUILD FOREX DONE ######## "

curl  -X POST http://94.239.109.172:9000/api/webhooks/83431043-97e7-4f1a-a341-d2214c1f93c2
