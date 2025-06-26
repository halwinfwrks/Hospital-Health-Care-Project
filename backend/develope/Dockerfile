From maven:3.8.6-openjdk-17 AS build
COPY ..
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/develope-0.0.1-SNAPSHOT.jar develope-0.0.1-SNAPSHOT.jar
EXPOSE 8989
ENTRYPOINT [ "java", "-jar", "develope-0.0.1-SNAPSHOT.jar" ]