FROM openjdk:alpine
ARG VERSION
ADD /target/chanchan-web-app-$VERSION.jar /app.jar
WORKDIR /
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]