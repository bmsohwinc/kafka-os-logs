package com.bms.kos.kafka;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bms.kos.kafka.KOSParsed.KOSLogEntry;
import com.bms.kos.kafka.serdes.KOSSerdes;
import com.bms.kos.service.LogSplitUtil;

@Configuration
public class KafkaStreamsConfig {

    @Value("${kafka.topic.name.raw.os.logs}")
    private String kafkaTopicName;

    @Value("${kafka.topic.name.streams.proto.logs}")
    private String kafkaStreamsTopicName;

    @Bean
    public KafkaStreams getKafkaStreamsInstance() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe-1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(kafkaTopicName);
        source.filter((key, value) -> LogSplitUtil.isDesiredString(value))
                .mapValues(value -> (LogSplitUtil.getKOSLogEntryFromParsedOSLogEntry(
                        LogSplitUtil.parseRecordFromOSLogEntry(value))))
                .to(kafkaStreamsTopicName, Produced.valueSerde(KOSSerdes.KOSLogEntry()));

        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);
        return streams;
    }
}
