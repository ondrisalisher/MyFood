FROM openjdk:21

COPY ./build/libs/MyFood-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8081

#LABEL authors="Alisher"
#
#ENTRYPOINT ["top", "-b"]