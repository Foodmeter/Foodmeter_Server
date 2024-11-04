FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .

RUN ./gradlew clean build -x test

CMD ["java", "-jar", "build/libs/Foodmeter-0.0.1-SNAPSHOT.jar"]

EXPOSE 8082

