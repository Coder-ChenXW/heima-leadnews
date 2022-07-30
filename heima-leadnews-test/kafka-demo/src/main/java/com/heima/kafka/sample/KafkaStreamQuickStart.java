package com.heima.kafka.sample;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Function: 功能描述 流式处理
 * @Author: ChenXW
 * @Date: 16:31 2022/7/30
 */
public class KafkaStreamQuickStart {

    public static void main(String[] args) {

        //kafka的配置信息
        Properties prop = new Properties();
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.200.130:9002");
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-quickstart");

        //stream 构建器
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        //流式计算
        streamProcessor(streamsBuilder);

        //创建kafka stream对象
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), prop);

        //开启流式计算
        kafkaStreams.start();
    }


    /**
     * @Function: 功能描述 流式计算
     * @Author: ChenXW
     * @Date: 16:36 2022/7/30
     */
    private static void streamProcessor(StreamsBuilder streamsBuilder) {
        //创建kstream对象,同时从topic中接受消息
        KStream<String, String> stream = streamsBuilder.stream("test-top-input");
        //处理消息的value值
        stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(String value) {
                return Arrays.asList(value.split(" "));
            }
        }).groupBy((key, value) -> value).windowedBy(TimeWindows.of(Duration.ofSeconds(10))).count().toStream().map((key, value) -> {
            System.out.println("key:"+key+",value:"+value);
            return new KeyValue<>(key.key().toString(), value);
        }).to("test-topic-out");
    }

}
