package com.bms.kos.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bms.kos.dao.KOSDao;
import com.bms.kos.domain.ParsedOSLogEntry;
import com.bms.kos.kafka.KOSParsed.KOSLogEntry;
import com.bms.kos.service.LogSplitUtil;

@Service
public class KOSConsumer {

    @Autowired
    private KOSDao kosDao;
     
    @KafkaListener(topics = "streams-test-output-2", groupId = "kos-consumer-grp")
    public void consume(KOSLogEntry kosLogEntry) {
        ParsedOSLogEntry parsedOSLogEntry = LogSplitUtil.getParsedOSLogEntryFromKOSLogEntry(kosLogEntry);
        System.out.println(parsedOSLogEntry);
        kosDao.save(parsedOSLogEntry);
    }
}
