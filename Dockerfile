# 1️⃣ Base image (Java runtime)
FROM eclipse-temurin:17-jdk-jammy

# 2️⃣ Create folder inside container
WORKDIR /app

# 3️⃣ Copy JAR into container
COPY target/elevate.jar elevate.jar

# 4️⃣ Expose application port
EXPOSE 8033

# 5️⃣ Command to run application
ENTRYPOINT ["java", "-jar", "elevate.jar"]
