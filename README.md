# Syncs OS Process Logs into Kafka


## Project scope
1. The file `/var/log/syslog` captures all process logs
2. Of the various logs, the ones of interest are those having a message starting with "Started" or "Successfully activated"
3. The message format follows a consistent pattern:
```
<Month><Date><Time><host><Process><Process ID><Started/Successfully activated><Service Name>
```
4. Project scope is to setup Kafka pipeline and enable Spark analysis, hence detailed metrics are not captured from syslog
5. The metrics that we will be analyzing:
    1. Most frequent service
    2. Most frequent daemon/process
    3. Number of processes started/activated at a given time
6. The fields that we'll capture to enable the above analyses:
    1. Date and Time
    2. Host name
    3. Process name
    4. Process ID
    5. Service name


## Run as
1. `mvn package -DskipTests`
2. `java -jar target/kos-0.0.1-SNAPSHOT.jar`


## Tools
1. Java Spring
2. Spring Kafka

## References
1. Downloading ProtoBuf Compiler [protoc](https://www.youtube.com/watch?v=46O73On0gyI)
2. Populating Repeated fields in [Protobuf class](https://stackoverflow.com/questions/29170183/how-to-set-repeated-fields-in-protobuf-before-building-the-message)
3. Saving Custom objects in [Kafka Topics](https://stackoverflow.com/questions/52450449/how-to-send-custom-object-to-kafka-topic-with-producer)
4. Basics about [various log files](https://www.crowdstrike.com/cybersecurity-101/observability/log-file/)
5. What all [we could do with System logs](https://www.linkedin.com/advice/0/what-some-techniques-analyzing-operating-system)
