# Use Maven base image to build the WAR file
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app

# Copy only the POM and resolve dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the project
COPY src ./src
RUN mvn clean package -DskipTests -Dspring.profiles.active=prod -Pwar

# Use OpenJDK 17 for the runtime environment
FROM openjdk:17-oracle
WORKDIR /app

# Copy the built WAR from the build stage
COPY --from=build /app/target/*.war /app/app.war

# Set the command to run the application
ENTRYPOINT ["java","-jar","/app/app.war"]



