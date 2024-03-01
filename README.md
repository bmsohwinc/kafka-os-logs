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

### Generate Protobuf Descriptor class file
1. Run the below commands (do not forget the `include_imports` flag, else dependencies won't be added to the descriptor)
```sh
$ ./protoc --include_imports -I /home/bms/my-proto/include -I /home/bms/projects/my-kafka/kos/src/main/resources/proto --descriptor_set_out=py-spark/KOSParsed.desc /home/bms/projects/my-kafka/kos/src/main/resources/proto/osparsed.proto
```

### Running PySpark to stream Protobuf records from Kafka
1. Run the below commands (do not forget the `packages` flag)
```sh
$ /home/bms/projects/my-spark/spark-3.5.0-bin-hadoop3/bin/spark-submit --packages "org.apache.spark:spark-protobuf_2.12:3.5.0","org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0" --master local[4] KStreamApp.py 
```

### Kafka console consumer
1. Read the records written to your kafka topic via the command:
```sh
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <KAFKA_TOPIC_NAME> --from-beginning
```

### Running the full application for a quick test
1. Start Zookeeper and Kafka main
```sh
$ cd /kafka/directory
$ bin/zookeeper-server-start.sh config/zookeeper.properties
$ bin/kafka-server-start.sh config/server.properties
```
2. Open Kafka console producer for topic-1
```sh
$ cd /kafka/directory
$ bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic kos_raw_os_log
```
3. Open Kafka console consumer for topic-1
```sh
$ cd /kafka/directory
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kos_raw_os_log --from-beginning
```
4. Open Kafka console consumer for topic-2
```sh
$ cd /kafka/directory
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-test-output-2 --from-beginning
```
5. Start KOSApplication
```sh
$ cd /project/directory
$ ./runner.sh
```
6. Start Kafka Streams
```sh
$ curl http://localhost:8080/kos/streams/start
```
7. Start PySpark Application
```sh
$ /home/bms/projects/my-spark/spark-3.5.0-bin-hadoop3/bin/spark-submit --packages "org.apache.spark:spark-protobuf_2.12:3.5.0","org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0" --master local[4] KStreamApp.py 
```
8. Write to Kafka producer of topic-1
```sh
> Feb 24 20:29:42 dell-Inspiron-15-3567 systemd[1]: Started Locale Service.
```
9. Check that records are logged onto:
    1. Topic-1 Console Consumer
    2. Topic-2 Console Consumer
    3. PySpark Console

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
10. Writing [custom Serdes](https://medium.com/@agvillamizar/implementing-custom-serdes-for-java-objects-using-json-serializer-and-deserializer-in-kafka-streams-d794b66e7c03)
11. Writing [Serializer and Deserializer for Protobuf classes](https://github.com/zuowang/kafka-protobuf/tree/master/src/main/java/kafka/serializer)
12. Serdes is just a combination of a serializer and a deserializer. You have to implement all 3 (Serializer, Deserializer, and Serdes) to use it in the Producer/Consumer/Streams
13. You can provide `custom Serdes` using `Consumed` and `Produced` classes in the `KStreams source` and `sink`, respectively
14. Reading [Kafka records from Spark](https://stackoverflow.com/a/41492614/9247555) using writeStream.start() and .awaitTermination()
15. Include necessary [protobuf](https://spark.apache.org/docs/latest/sql-data-sources-protobuf.html#deploying) and [kafka](https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html#deploying) packages in spark-submit command line
16. Creating [Protobuf descriptor files](https://docs.streamsets.com/portal/platform-datacollector/latest/datacollector/UserGuide/Data_Formats/Protobuf-Prerequisites.html)
17. Good article on [spark streaming with kafka](https://subhamkharwal.medium.com/pyspark-structured-streaming-read-from-kafka-64c40767155f)
18. Creating a simple [Spring JPA](https://medium.com/@khairmuhammadmemon/spring-boot-data-access-with-spring-data-jpa-and-mysql-afe90e28b05d) project with MySQL
19. Configuring [Kafka consumer](https://www.youtube.com/watch?v=d0T9g0_G_3U)
20. [Starting MySQL service](https://stackoverflow.com/a/23485424/9247555) if down or cannot connect
