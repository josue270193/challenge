# challenge_calculator
A Java project

## Component / Tools / Library
- Java 21+
- Spring Boot 3+
- PostgresSQL
- Apache Kafka
- Docker
- Gradle

## Getting started

The project uses Docker to implement all the components locally. Make sure you have Docker installed
in your system. Then use the internal gradle implementation.

``
./gradlew bootRun
``

### Docker 

[Docker Hub repository](https://hub.docker.com/r/josue270193/challenge_calculator)

There is an image deployed in Docker Hub ready to use and deploy locally using docker.

To build an image locally

``
./gradlew bootBuildImage
``

Then with docker can run locally and run the others components. 
Also, you could use the compose.yaml file as a template. 
I recommend use the `./gradlew bootRun` command. 

``
docker run -p 8080:8080 -t docker.io/josue270193/challenge_calculator:0.0.2-SNAPSHOT
``

### Test endpoint

In order to test all the endpoint please open a browser and go to webpage.
A swagger page will provide you the access to play with it.

``
http://localhost:8080
``

