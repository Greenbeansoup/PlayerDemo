FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
ARG JAR_FILE=target/player-demo-0.0.1-SNAPSHOT.jar
ARG PLAYERS=src/main/resources/Players.csv
ADD ${JAR_FILE} app.jar
ADD ${PLAYERS} .
ENTRYPOINT ["java","-jar","/app.jar"]