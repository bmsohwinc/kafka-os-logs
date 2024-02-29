package com.bms.kos.kafka.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bms.kos.kafka.KOSParsed.KOSLogEntry;
import com.google.protobuf.InvalidProtocolBufferException;

public class KOSParsedDeserializer extends Adapter implements Deserializer<KOSLogEntry> {
    private static final Logger LOG = LoggerFactory.getLogger(KOSParsedDeserializer.class);

    @Override
    public KOSLogEntry deserialize(String topic, byte[] data) {
        try {
            return KOSLogEntry.parseFrom(data);
        } catch (final InvalidProtocolBufferException e) {
            LOG.error("Received unparseable message", e);
            throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
        }
    }
    
}
