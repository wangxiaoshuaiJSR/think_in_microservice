package org.architect.wxs.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/5
 * @javadoc ：kafka消费端
 */
@Component
public class KafkaConsumerListener {
    @KafkaListener(topics = "test")
    public void onMessage(String message){
        System.err.println("监听到的消息"+message);
    }
}
