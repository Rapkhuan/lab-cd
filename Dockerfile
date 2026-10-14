FROM maven:3.9-eclipse-temurin-17 AS build

COPY src /home/app/src
COPY pom.xml /home/app/pom.xml

RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]