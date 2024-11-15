# Stage 1: Build stage
# Create an image called build from the openJDK image
# This image is in charge of creating and unpacking 
FROM openjdk:17 as build
LABEL maintainer="Illia Babich"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Extract JAR file contents to make it layer-friendly
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

# Stage 2: Run stage
# Copy the different layers into the new image
FROM openjdk:17
VOLUME [ "/tmp" ]
ARG DEPENDENCY=/target/dependency

# Copy dependencies and classes from build stage
# This folder contains all of the internal and external dependencies needed to run the application
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set the active Spring profile to 'docker' for container environment
ENV SPRING_PROFILES_ACTIVE=docker

# Target the licensing service application in the new image when the container is created
ENTRYPOINT [ "java", "-cp", "-Dspring-boot.run.profiles=docker", "app:app/lib/*", "com.optimagrowth.licensing_service.LicensingServiceApplication" ]