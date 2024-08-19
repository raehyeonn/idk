FROM openjdk:21-jdk

WORKDIR /app

COPY /build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "app.jar", "&"]

