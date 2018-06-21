FROM maven:3.3-jdk-8
EXPOSE 8080
COPY . /app
WORKDIR /app/
CMD mvn spring-boot:run