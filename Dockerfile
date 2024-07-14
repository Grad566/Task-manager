FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY /app .

RUN gradle build

CMD java -jar build/libs/app-0.0.1-SNAPSHOT.jar
