FROM openjdk:17-jdk-alpine
LABEL authors="Ivan"
ADD target/MoneyTransfer-0.0.1-SNAPSHOT.jar myApp.jar
EXPOSE 5500
ENTRYPOINT ["java", "-jar", "myApp.jar"]