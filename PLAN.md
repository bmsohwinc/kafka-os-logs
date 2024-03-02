- [x] Phase 1: Explore OS logs
    - [x] Read and understand OS Process logs
    - [x] Check how to process the logs
    - [x] Check what metrics can be captured
    - [x] Check what additional data can be captured
    - [x] Observations recorded in **README.md** under *Project scope*

- [x] Phase 2: Capture OS logs in Java
    - [x] Check if File reading can be used to capture logs
    - [x] Check how to parse the logs

- [x] Phase 3: Store OS logs in Kafka
    - [x] Setup producer for topic-1
    - [x] Store plain strings in topic-1
    - [x] Setup Kafka Stream to read from topic-1 and write to topic-2
    - [x] Create proto file
    - [x] Write in protobuf format to topic-2

- [x] Phase 4: Setup job to push to Kafka
    - [x] Check how to periodically push logs to Kafka
    - [x] Check how to handle faults
    - [x] Check how to prevent re-reads

- [x] Phase 5: Setup job using Airflow
    - [x] Explore Airflow
    - [x] Check how to schedule jobs with it
    - [x] Test on this application

- ~~[ ] Phase 6: Process OS Kafka logs~~ (We used KStreams to process logs in **Phase 3**)
    - ~~[ ] Check what metrics can be extracted from raw logs~~
    - ~~[ ] Check how to use Kafka Streams to generate the aggregates~~

- [x] Phase 7: Store OS Kafka logs in SQL
    - [x] Setup consumer to listen to processed topic
    - [x] Store the processed logs into an SQL table

- [x] Phase 8: Read OS SQL logs with Spark
    - [x] Check how to setup Spark with SQL
    - [x] Check basic syntax
    - [x] Check what visualizaions can be derived
    - ~~[ ] Check how to setup a dashboard (if possible)~~

- [x] Phase 9: Run entire system ~~and make video/gif~~

- [x] Phase 10: Refactor code, document, and commit
