- [x] Phase 1: Explore OS logs
    - [x] Read and understand OS Process logs
    - [x] Check how to process the logs
    - [x] Check what metrics can be captured
    - [x] Check what additional data can be captured
    - [x] Observations recorded in **README.md** under *Project scope*

- [ ] Phase 2: Capture OS logs in Java
    - [x] Check if File reading can be used to capture logs
    - [ ] Check how to parse the logs

- [ ] Phase 3: Store OS logs in Kafka
    - [ ] Create proto file
    - [ ] Setup Producer and Consumer

- [ ] Phase 4: Setup job to push to Kafka
    - [ ] Check how to periodically push logs to Kafka
    - [ ] Check how to handle faults
    - [ ] Check how to prevent re-reads

- [ ] Phase 5: Setup job using Airflow
    - [ ] Explore Airflow
    - [ ] Check how to schedule jobs with it
    - [ ] Test on this application

- [ ] Phase 6: Process OS Kafka logs
    - [ ] Check what metrics can be extracted from raw logs
    - [ ] Check how to use Kafka Streams to generate the aggregates

- [ ] Phase 7: Store OS Kafka logs in SQL
    - [ ] Setup consumer to listen to processed topic
    - [ ] Store the processed logs into an SQL table

- [ ] Phase 8: Read OS SQL logs with Spark
    - [ ] Check how to setup Spark with SQL
    - [ ] Check basic syntax
    - [ ] Check what visualizaions can be derived
    - [ ] Check how to setup a dashboard (if possible)

- [ ] Phase 9: Run entire system and make video/gif

- [ ] Phase 10: Refactor code, document, and commit
