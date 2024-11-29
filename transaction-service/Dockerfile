FROM openjdk:17
ARG JAR_FILE=transaction-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080