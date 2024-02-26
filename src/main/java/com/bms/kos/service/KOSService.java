package com.bms.kos.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bms.kos.domain.ParsedOSLogEntry;

@Service
public class KOSService {

    @Value("${syslog.path}")
    private String syslogPath;

    public String readSysLogs() throws IOException {
        List<ParsedOSLogEntry> parsedOSLogEntries = new ArrayList<>();

        try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(syslogPath);
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                // fileContent.append(line).append("\n");
                if (LogSplitUtil.isDesiredString(line)) {
                    parsedOSLogEntries.add(LogSplitUtil.parseRecordFromOSLogEntry(line));
                }
            }
        }

        System.out.println("No. of logs parsed: " + parsedOSLogEntries.size());
        System.out.println("----------------------------");
        parsedOSLogEntries.stream()
                .limit(5)
                .forEach(System.out::println);

        System.out.println("----------------------------");
        return "OK";
    }

}
