FROM java:8
VOLUME /pomangamVolume
EXPOSE 9530
ARG JAR_FILE=build/libs/pomangam-api-monilith-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]