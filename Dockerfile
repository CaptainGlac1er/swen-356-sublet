FROM java:8

RUN apt-get update
RUN apt-get install -y maven
RUN apt-get install -y tree

WORKDIR /code
# Prepare by downloading dependencies
ADD pom.xml pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src src
RUN ["mvn", "package"]

EXPOSE 4000
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/sublet-1.0-SNAPSHOT-jar-with-dependencies.jar"]
