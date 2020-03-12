package org.architect.wxs.stream.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/6
 * @javadoc ：name = "gupao";要和配置文件保持一致
 */
public interface MessageSource {

    String name = "gupao";

    @Output(name)
    MessageChannel gupao();
}
