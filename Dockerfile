FROM openjdk:17

COPY target/expenseTrackerApi.jar expenseTrackerApi.jar

ENTRYPOINT ["java", "-jar", "/expenseTrackerApi.jar"]