package org.architect.wxs.raw.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/5
 * @javadoc ：原生的API操作kafka
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","192.168.0.106:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());
        KafkaProducer<String,String> kafkaProducer=new KafkaProducer(properties);
        //发送kafka消息
        String topic="test";
        Integer partition=0;
        Long timestamp = System.currentTimeMillis();
        String key = "message-key";
        String value = "xiaoshuaige";
        ProducerRecord<String,String> record=
                new ProducerRecord<String,String>(topic,partition,key,value);
        Future<RecordMetadata> send = kafkaProducer.send(record);
        //等待发送结果
        RecordMetadata metadata = send.get();
        System.err.println(metadata);

    }
}
