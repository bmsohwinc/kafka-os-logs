package com.bms.kos.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.bms.kos.kafka.KOSParsed.KOSLogEntry;;

public class KOSParsedSerializer extends Adapter implements Serializer<KOSLogEntry> {

    @Override
    public byte[] serialize(String topic, KOSLogEntry data) {
        return data.toByteArray();
    }

}
