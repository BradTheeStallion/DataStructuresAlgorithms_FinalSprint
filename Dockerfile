FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    && wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh -O /wait-for-it.sh \
    && chmod +x /wait-for-it.sh \
    && apt-get purge -y --auto-remove wget \
    && rm -rf /var/lib/apt/lists/*

COPY pom.xml /app/

RUN mvn dependency:go-offline -B \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
    -Dmaven.wagon.http.retryHandler.count=3 \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=180 \
    -Dhttp.keepAlive=false

COPY src /app/src

RUN export MAVEN_OPTS="-Xmx1024m" && \
    mvn clean package -DskipTests -B \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
    -Dmaven.wagon.http.retryHandler.count=3 \
    --fail-at-end \
    || (echo "Build failed. Check logs below:" && cat target/surefire-reports/*.txt && exit 1)

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /wait-for-it.sh /wait-for-it.sh

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["/bin/bash", "-c", "/wait-for-it.sh ${DATABASE_HOST:-db}:${DATABASE_PORT:-3306} -t 60 -- java -jar app.jar"]