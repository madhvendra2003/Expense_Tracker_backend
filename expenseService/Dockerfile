FROM eclipse-temurin:21
WORKDIR /usr/src/app
COPY build/libs/expenseService-0.0.1-SNAPSHOT.jar expense.jar
ENTRYPOINT ["java", "-jar", "expense.jar"]
EXPOSE 9910