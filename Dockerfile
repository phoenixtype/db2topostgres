# Fetch base image
FROM openjdk:17-slim as builder

# Set working directory
WORKDIR /workspace/app

# Copy Maven configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fetch project dependencies
RUN ./mvnw dependency:go-offline

# Copy project files
COPY src src

# Build the project
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Fetch base image for the runtime
FROM openjdk:17-slim

# Set volume
VOLUME /tmp

# Copy over dependencies from the build image
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /workspace/app/target/dependency/META-INF /app/META-INF
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/classes /app

# Set entry point
ENTRYPOINT ["java","-cp","app:app/lib/*","dev.phoenixtype.postgres2db2.Postgres2db2Application"]
