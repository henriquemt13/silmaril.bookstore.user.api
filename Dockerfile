# Build Stage
FROM amazoncorretto:21-alpine3.21-jdk AS build
WORKDIR /build
COPY . .
RUN apk add maven=3.9.9-r0


# Getting version info to build package
RUN mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.name -q -DforceStdout > name
RUN mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout > version
RUN mvn package -DskipTests
RUN mv ./target/`cat name`-`cat version`.jar ./application.jar

# Distribution Stage
FROM amazoncorretto:21-alpine3.21-jdk AS runner
WORKDIR /app
COPY --from=build /build/*.jar ./application.jar
EXPOSE 8080
CMD [ "java", "-jar", "application.jar" ]