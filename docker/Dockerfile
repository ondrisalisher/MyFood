FROM openjdk:21

ARG APP_JAR=*.jar

COPY ${APP_JAR} MyFood.jar

ENTRYPOINT ["java", "-jar", "MyFood.jar"]

#EXPOSE 8080

#LABEL authors="Alisher"
#
#ENTRYPOINT ["top", "-b"]