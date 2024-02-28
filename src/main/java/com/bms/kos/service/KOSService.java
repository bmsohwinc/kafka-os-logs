package com.bms.kos.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bms.kos.kafka.KOSProducer;

@Service
public class KOSService {

    @Value("${syslog.path}")
    private String syslogPath;

    @Autowired
    private KOSProducer kosProducer;

    public String readSysLogs() throws IOException {
        int numRecordsInserted = 0;
        String kafkaReturnValue = null;
        try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(syslogPath);
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                kafkaReturnValue = kosProducer.sendMessage(line);
                numRecordsInserted++;
            }
        }
        System.out.println("No. of logs inserted: " + numRecordsInserted);
        return kafkaReturnValue;
    }

}
