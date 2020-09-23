#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-forex")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-forex
docker push ezzefiohez/finance-forex
