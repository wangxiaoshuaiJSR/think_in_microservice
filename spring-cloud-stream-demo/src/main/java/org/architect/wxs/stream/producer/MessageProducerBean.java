package org.architect.wxs.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/5
 * @javadoc ：MessageChannel 不能和org.apache.kafka.common.serialization.StringSerializer共存
 */
@Component
@EnableBinding({Source.class,MessageSource.class})
public class MessageProducerBean {
    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Autowired
    @Qualifier(MessageSource.name)
    private MessageChannel messageChannel2;

    @Autowired
    private Source source;

    public void send(String message){
        //通过管道发送消息
     //   messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }


}
