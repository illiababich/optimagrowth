# Stage 1
# Create an image called build from the openJDK image
# This image is in charge of creating and unpacking 
FROM openjdk:17 as build
LABEL maintainer="Illia Babich"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

# Stage 2
# Copy the different layers into the new image
FROM openjdk:17
VOLUME [ "/tmp" ]
ARG DEPENDENCY=/target/dependency
# This folder contains all of the internal and external dependencies needed to run the application
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Target the licensing service application in the new image when the container is created
ENTRYPOINT [ "java", "-cp", "app:app/lib/*", "com.optimagrowth.configserver.ConfigserverApplication" ]