package com.bms.kos.kafka.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.bms.kos.kafka.KOSParsed.KOSLogEntry;

public class KOSSerdes {
    private KOSSerdes() {}

    public static Serde<KOSLogEntry> KOSLogEntry() {
        KOSParsedSerializer serializer = new KOSParsedSerializer();
        KOSParsedDeserializer deserializer = new KOSParsedDeserializer();
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
