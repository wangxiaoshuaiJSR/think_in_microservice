package org.architect.wxs.web.controller;

import org.architect.wxs.stream.producer.MessageProducerBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link KafkaAutoConfiguration}
 *
 * @author wangxiaoshuai on 2020/3/5
 * @javadoc ：KafkaTemplate 已经注入到容器中了
 */
@RestController
public class KafkaProducerController {
    private final KafkaTemplate kafkaTemplate;
    private final MessageProducerBean messageProducerBean;

    public KafkaProducerController(KafkaTemplate kafkaTemplate, MessageProducerBean messageProducerBean) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageProducerBean = messageProducerBean;
    }
    @GetMapping("/send")
    public void sendMessage(@RequestParam String value){
        kafkaTemplate.send("test",value);
    }

    @GetMapping("/stream")
    public void sendStream(@RequestParam String value){
        messageProducerBean.send(value);
    }


}
