FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY . .
RUN ./mvnw clean package -Dmaven.test.skip
RUN cp target/*.jar app.jar
ENTRYPOINT ["java","-jar","./app.jar"]