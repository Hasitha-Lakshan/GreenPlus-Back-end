FROM openjdk:8-alpine
EXPOSE 8080
ADD target/greenplus-app-backend.jar greenplus-app-backend.jar
ENTRYPOINT ["java","-jar","/greenplus-app-backend.jar"]