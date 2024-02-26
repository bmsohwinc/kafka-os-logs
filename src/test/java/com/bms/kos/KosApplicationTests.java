package com.bms.kos;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bms.kos.service.LogSplitUtil;

@SpringBootTest
class KosApplicationTests {

	@Test
	void checkLogSplitUtil() {
		String finalString = "Feb 24 00:17:34 dell-Inspiron-15-3567 dbus-daemon[1016]: [system] Successfully activated service 'org.freedesktop.PackageKit'";
		System.out.println(LogSplitUtil.parseRecordFromOSLogEntry(finalString));
		assertTrue(true);
	}

}
