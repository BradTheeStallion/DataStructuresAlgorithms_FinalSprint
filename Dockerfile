FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
RUN apt-get update && apt-get install -y wget && \
    wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh -O /wait-for-it.sh && \
    chmod +x /wait-for-it.sh && \
    apt-get purge -y --auto-remove wget && \
    rm -rf /var/lib/apt/lists/*
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /wait-for-it.sh /wait-for-it.sh
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["/bin/bash", "-c", "/wait-for-it.sh ${DATABASE_HOST:-db}:${DATABASE_PORT:-3307} -t 60 -- java -jar app.jar"]