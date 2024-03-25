# Build
FROM maven:latest AS build
WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn -B clean package -DskipTests

# Deploy

FROM tomcat:latest

COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps