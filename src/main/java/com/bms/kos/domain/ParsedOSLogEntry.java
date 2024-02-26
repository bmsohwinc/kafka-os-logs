package com.bms.kos.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParsedOSLogEntry {
    private LocalDateTime logDateTime;
    
    private String hostName;

    private String processName;

    private String processId;

    private String serviceName;
}
