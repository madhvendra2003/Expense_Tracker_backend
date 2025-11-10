FROM  eclipse-temurin:21
WORKDIR /usr/src/app
COPY build/libs/UserService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 9810