package com.bms.kos.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

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
        
        File file = new File(syslogPath);
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                kafkaReturnValue = kosProducer.sendMessage(input.nextLine());
                numRecordsInserted++;
            }
            input.close();
        }
        System.out.println("No. of logs inserted: " + numRecordsInserted);
        return kafkaReturnValue;
    }

}
