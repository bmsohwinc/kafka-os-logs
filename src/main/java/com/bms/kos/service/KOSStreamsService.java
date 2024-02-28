package com.bms.kos.service;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KOSStreamsService {

    @Autowired
    private KafkaStreams kafkaStreams;

    public String startStreams() {
        kafkaStreams.start();
        return "KStreams says: OK";
    }

    public String stopStreams() {
        kafkaStreams.close();
        return "KStreams says: OK";
    }
    
}
