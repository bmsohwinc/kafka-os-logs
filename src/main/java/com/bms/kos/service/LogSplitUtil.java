package com.bms.kos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bms.kos.domain.ParsedOSLogEntry;

public final class LogSplitUtil {

    public static final String SUCCESSFULLY_ACTIVATED_SERVICE = "Successfully activated service";

    public static final String STARTED_SERVICE = "Started";

    public static final String DELIMITER_WHITESPACE = " ";

    private LogSplitUtil() {
    }

    /**
     * Returns true if the desired substring is present in the given Log entry.
     * 
     * @param logString
     * @return {true | false}
     */
    public static boolean isDesiredString(String logString) {
        return logString.contains(SUCCESSFULLY_ACTIVATED_SERVICE) || logString.contains(STARTED_SERVICE);
    }

    public static ParsedOSLogEntry parseRecordFromOSLogEntry(String inputLogString) {
        String[] splitLogTokens = inputLogString.split(DELIMITER_WHITESPACE);
        if (splitLogTokens.length < 7) {
            System.out.println("Invalid Syslog entry. Total token size < 7: " + splitLogTokens.length);

            throw new IllegalStateException("Given inputLogString has fewer "
                    + "than desired number of tokens. No. of tokens parsed = "
                    + splitLogTokens.length);
        }

        ParsedOSLogEntry parsedOSLogEntry = new ParsedOSLogEntry();
        parsedOSLogEntry.setLogDateTime(getDateTime(splitLogTokens));
        parsedOSLogEntry.setHostName(splitLogTokens[3]);

        String processNameWithId = splitLogTokens[4];
        String[] processNameWithIdTokens = processNameWithId.split("\\[|\\]");
        if (processNameWithIdTokens.length > 0) {
            parsedOSLogEntry.setProcessName(processNameWithIdTokens[0]);
        }

        if (processNameWithIdTokens.length > 1) {
            parsedOSLogEntry.setProcessId(processNameWithIdTokens[1]);
        }

        parsedOSLogEntry.setServiceName(getServiceName(splitLogTokens));

        return parsedOSLogEntry;
    }

    private static LocalDateTime getDateTime(String[] tokens) {
        String dateInString = LocalDate.now().getYear() + DELIMITER_WHITESPACE + tokens[0]
                + DELIMITER_WHITESPACE + tokens[1]
                + DELIMITER_WHITESPACE + tokens[2];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
        LocalDateTime logDateTime = LocalDateTime.parse(dateInString, formatter);

        return logDateTime;
    }

    // TODO: Optimize by using plain string as input rather than tokens
    private static String getServiceName(String[] tokens) {
        String serviceName = "";

        int totalTokensLength = tokens.length;
        int startIndex = totalTokensLength;

        for (int i = 5; (i + 2) < totalTokensLength; i++) {
            String nextThreeTokens = tokens[i] + DELIMITER_WHITESPACE + tokens[i + 1]
                    + DELIMITER_WHITESPACE + tokens[i + 2];
            if (SUCCESSFULLY_ACTIVATED_SERVICE.equals(nextThreeTokens)) {
                startIndex = i + 3;
                break;
            } else if (STARTED_SERVICE.equals(tokens[i])) {
                startIndex = i + 1;
                break;
            }
        }

        StringBuilder serviceNameTemp = new StringBuilder();
        for (int j = startIndex; j < totalTokensLength; j++) {
            serviceNameTemp.append(tokens[j]).append(DELIMITER_WHITESPACE);
        }
        serviceName = serviceNameTemp.toString();

        return serviceName;
    }

}
