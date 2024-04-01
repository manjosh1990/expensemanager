./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=manjosh1990/expensetracker-api

./mvnw spring-boot:build-image

#builds image and pushes to docker hub
./mvnw jib:build

#build image locally, needs docker desktop running
./mvnw jib:build:dockerBuild