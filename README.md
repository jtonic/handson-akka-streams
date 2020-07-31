# Sample project to learn Akka streams and Alpakka (Kafka, Cassandra)

## Tasks:

- [x] set up the project
- [x] create the docker-compose (kafka, cassandra)
- [x] create a simple alpakka kafka producer which produce kafka messages pushed by an iterator source
- [ ] Configure the flow to serialize to JSON
- [ ] Configure the Akka Telemetry (developer sandbox) to tune the performance (fusion)
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

    - First run the docker containers with the shared IntelliJ Idea Run Configuration or `docker-compose up`
    
    - Use the shared IntelliJ Idea Run Configurations

## Recommendations

- In order to use the same maven version use IntelliJ Idea 2020.2 which supports [maven wrapper](https://blog.jetbrains.com/idea/2020/05/intellij-idea-2020-2-early-access-program-is-starting/)

## Documentation and examples

- [SB and alpakka example](https://medium.com/@lprakashv/akka-streams-in-java-spring-boot-f7749cafb7f5)
- [Github example](https://github.com/daggerok/spring-boot-reactive-scala-example/blob/master/src/main/scala/com/github/daggerok/SpringBootScalaApplication.scala)
- [Alpakka samples](https://github.com/akka/alpakka-samples)
