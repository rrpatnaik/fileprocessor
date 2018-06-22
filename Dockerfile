FROM maven:3.3-jdk-8
EXPOSE 8080
COPY . /app
WORKDIR /app/
RUN mvn package spring-boot:repackage
CMD java -Dserver.port=$PORT -jar target/loc-time-0.0.1-SNAPSHOT.jar


# Run the app.  CMD is required to run on Heroku
# $PORT is set by Heroku			
# CMD gunicorn --bind 0.0.0.0:$PORT wsgi 

