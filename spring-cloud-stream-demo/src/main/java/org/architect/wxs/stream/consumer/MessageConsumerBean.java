package org.architect.wxs.stream.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/6
 * @javadoc ：
 */
@Component
@EnableBinding({Sink.class})
public class MessageConsumerBean {

    @Autowired
    @Qualifier(Sink.INPUT)
    SubscribableChannel subscribableChannel;

    @Autowired
    private Sink sink;

    @PostConstruct
    public void init(){
        subscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
            }
        });
    }

    //通过ServiceActivator监听
    @ServiceActivator(inputChannel = Sink.INPUT)
    public void onMessage(Object message){
        System.out.println(message);
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(String message){
        System.out.println(message);
    }

}
