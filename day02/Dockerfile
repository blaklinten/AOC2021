FROM maven:openjdk
WORKDIR /src
COPY ./ .
RUN mvn clean compile assembly:single

COPY input.txt .
CMD java -jar target/Solution-1.0-SNAPSHOT-jar-with-dependencies.jar
