# Use the official maven/Java 17 image to create a build artifact.
# https://hub.docker.com/_/maven
#FROM maven:3.5-jdk-8-alpine as builder PRECEDENT
FROM maven:3.8.1-openjdk-17 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml ./
COPY src ./src/

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
#FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim
FROM mediasol/openjdk17-slim-jprofiler
# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/*.jar /helloworld.jar

# Run the web service on container startup.
#NON# CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/helloworld.jar"]
#OK# CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=8080","-jar","/helloworld.jar"]
CMD ["java","-jar","/helloworld.jar"]