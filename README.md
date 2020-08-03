# Sample project to learn Akka streams and Alpakka (Kafka, Cassandra)

## Tasks:

- [x] set up the project
- [x] create the docker-compose (kafka, cassandra)
- [x] create a simple alpakka kafka producer which produce kafka messages pushed by an iterator source
- [ ] Configure the flow to serialize to JSON
- [x] Configure the Akka Telemetry (developer sandbox) to tune the performance (fusion)
- [ ] Create a simple alpakka kafka consumer and stream it to the console
- [ ] Log configuration for:
    - [ ] Akka actors
    - [ ] Akka stream materializer
    - [ ] Alpakka Kafka integration
- [ ] UT for alpakka flows
- [ ] IT for alpakka flows
- [ ] runnable graph to stream data from kafka topic into the cassandra storage


## How prepare the stage and run the application

- run the application

    - First run the docker containers with the shared Intellij Idea Run Configuration or `docker-compose up`
    
    - Use the shared Intellij Idea Run Configurations

- the kafkaProducer maven module has been configured for Lightbend Telemetry (except for the credentials) and Lightbend prometheus developer sandbox.

    - To run the kafka producer application (for which the runner has been provided) the Lightbend credentials for telemetry are required.
    See [Lightbend Telemetry](#Lightbend Telemetry) how to get Lightbend telemetry credentials.
    - Apart from running the kafka producer application with the idea shared run configuration it could be run with maven
    
    ```shell script
    $ cd  kafkaProducer
    $ mvn clean spring-boot:run -Pmetrics
    ```
    
## Monitoring the application:

  - See [here](https://developer.lightbend.com/docs/telemetry/current/sandbox/prometheus-sandbox.html) how to download the lightbend prometheus sandbox     
  - Unzip it into `docker/akka-streams` (the content of the unzipped root directory)
  - Run the following commands
  
  ```shell script
    $ cd  docker/akka-streams
    $ docker-compose up
  ```
  - Point the browser to both:
    - [Kafka control center](http://localhost:9021) 
    - [Lightbend telemetry](http://localhost:3000)

## Recommendations
- In order to use the same maven version use Intellij Idea 2020.2 which supports [maven wrapper](https://blog.jetbrains.com/idea/2020/05/intellij-idea-2020-2-early-access-program-is-starting/)

## Documentation and examples

- [SB and alpakka example](https://medium.com/@lprakashv/akka-streams-in-java-spring-boot-f7749cafb7f5)
- [Github example](https://github.com/daggerok/spring-boot-reactive-scala-example/blob/master/src/main/scala/com/github/daggerok/SpringBootScalaApplication.scala)
- [Alpakka samples](https://github.com/akka/alpakka-samples)

## Lightbend Telemetry
  - [How to install prometheus sandbox](https://developer.lightbend.com/docs/telemetry/current/sandbox/prometheus-sandbox.html)
  - [getting started](https://developer.lightbend.com/docs/telemetry/2.14.x/getting-started/akka_maven.html)
  - [akka streams](https://developer.lightbend.com/docs/telemetry/current/instrumentations/akka-streams/akka-stream-configuration.html)
  
  - **DON'T USE LIGHTBEND TELEMETRY IN PROD!!!** 
  - [Getting the developer non-production credentials for telemetry](https://www.lightbend.com/account/lightbend-platform/credentials)
  - How to configure lightbend telemetry for maven
    
    After you get the credentials add the following in the ~/.m2/settings.xml
    
    ```xml
    <settings>
        <server>
            <id>lightbend-commercial</id>
            <username>user_name</username>
            <password>password</password>
        </server>
        <repository>
            <id>lightbend-commercial</id>
            <name>Lightbend Commercial</name>
            <url>https://lightbend.bintray.com/commercial-releases</url>
        </repository>
    </settings>
    ```  
 