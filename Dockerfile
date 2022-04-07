FROM openjdk:11-jdk
EXPOSE 8888
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "application.jar"]