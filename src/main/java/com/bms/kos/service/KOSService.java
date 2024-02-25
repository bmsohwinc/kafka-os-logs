package com.bms.kos.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KOSService {

    @Value("${syslog.path}")
    private String syslogPath;
    
    public String readSysLogs() throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (InputStream iStream = getClass().getClassLoader().getResourceAsStream(syslogPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
            }
        System.out.println(fileContent.toString());
        return "OK";
    }
    
}
