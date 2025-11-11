FROM  eclipse-temurin:21
WORKDIR /usr/src/app
COPY app/build/libs/app.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]
EXPOSE 9898