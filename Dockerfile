FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY /app .

RUN gradle installBootDist

CMD /app/build/install/app/bin/app
