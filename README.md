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


## Commands
### Run the code
```sh
$ mvn package -DskipTests
$ java -jar target/kos-0.0.1-SNAPSHOT.jar
```
OR
```sh
$ ./runner.sh
```

### Generate Protobuf class file
1. Create the `osparsed.proto` file in resources/ directory
2. Import necessary `google/*.proto` files in the `osparsed.proto` file (`import "google/protobuf/timestamp.proto";` in this case)
3. Note down full path to the directory where `osparsed.proto` resides and the full path where the protoc's `include` directory resides
4. Run the below commands
```sh
$ cd /path/to/protoc
$ ./protoc -I /home/bms/my-proto/include -I /home/bms/projects/my-kafka/kos/src/main/resources/proto --java_out=/home/bms/projects/my-kafka/kos/src/main/java/ /home/bms/projects/my-kafka/kos/src/main/resources/proto/osparsed.proto
```

### Kafka console consumer
1. Read the records written to your kafka topic via the command:
```sh
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <KAFKA_TOPIC_NAME> --from-beginning
```

## Tools
1. Java Spring
2. Spring Kafka

## References
1. Downloading ProtoBuf Compiler [protoc](https://www.youtube.com/watch?v=46O73On0gyI)
2. Populating Repeated fields in [Protobuf class](https://stackoverflow.com/questions/29170183/how-to-set-repeated-fields-in-protobuf-before-building-the-message)
3. Saving Custom objects in [Kafka Topics](https://stackoverflow.com/questions/52450449/how-to-send-custom-object-to-kafka-topic-with-producer)
4. Basics about [various log files](https://www.crowdstrike.com/cybersecurity-101/observability/log-file/)
5. What all [we could do with System logs](https://www.linkedin.com/advice/0/what-some-techniques-analyzing-operating-system)
6. Providing [correct paths](https://stackoverflow.com/a/56033787/9247555) for protobuf compilation
7. [Compiling](https://protobuf.dev/getting-started/javatutorial/#compiling-protocol-buffers) protobuf files
8. Properly [importing external or library proto files](https://stackoverflow.com/a/49092821/9247555) in main proto file
9. Converting [LocalDateTime to Protobuf Timestamp](https://stackoverflow.com/a/66162818/9247555)
