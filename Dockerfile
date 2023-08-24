# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-slim
LABEL authors="samuel.akuma"

# Set the working directory in the container
WORKDIR /app

# Copy the Maven pom.xml and source code to the container
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the application without running tests
#RUN mvn clean package -DskipTests

# Copy the application's JAR file inside the container
COPY target/db2topostgres-0.0.1-SNAPSHOT.jar /app/db2topostgres.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "/app/db2topostgres.jar"]

# Expose the application's port (assuming the default port 8098, but you might need to adjust this)
EXPOSE 8098


# -- OLD DOCKERFILE --

## Use the official OpenJDK 17 base image
#FROM openjdk:17-jdk-slim
#LABEL authors="samuel.akuma"
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the application's JAR file inside the container
#COPY target/db2topostgres-0.0.1-SNAPSHOT.jar /app/db2topostgres.jar
#
## Command to run the Spring Boot application
#CMD ["java", "-jar", "/app/db2topostgres.jar"]
#
## Expose the application's port (assuming the default port 8098, but you might need to adjust this)
#EXPOSE 8098
