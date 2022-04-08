FROM openjdk:8-jdk-alpine
MAINTAINER Abubakar
COPY target/topup-0.0.1-SNAPSHOT.jar wallet-topup.jar
ENTRYPOINT ["java","-jar","/wallet-topup.jar"]