# create .jar file
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/englishweb/h2t_backside/H2tBacksideApplication.java /home/app/src/main/java/com/englishweb/h2t_backside/H2tBacksideApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

# create docker image
FROM openjdk:18-slim
EXPOSE 8080
COPY --from=build /home/app/target/*.jar /app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]