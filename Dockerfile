# Use Maven base image with JDK
FROM maven:3.8.1-openjdk-17 as builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file into our app directory
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of our application
COPY src /app/src

# Build and run tests
RUN mvn verify

# Start a new stage for running the application
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sakshigarg1602@gmail.com"

# Expose port 8080
EXPOSE 8080

# Copy the jar file from the builder stage
COPY --from=builder /app/target/fetch-0.0.1-SNAPSHOT.jar /app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
