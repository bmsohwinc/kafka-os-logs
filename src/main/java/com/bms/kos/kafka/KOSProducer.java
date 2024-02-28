package com.bms.kos.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KOSProducer {

    @Value("${kafka.topic.name.raw.os.logs}")
    private String kafkaTopicName;
    
    @Autowired
    private KafkaTemplate<String, String> messageProducer;

    public String sendMessage(String osLogEntry) {
        messageProducer.send(kafkaTopicName, osLogEntry);
        return "Producer says: OK";
    }

}
