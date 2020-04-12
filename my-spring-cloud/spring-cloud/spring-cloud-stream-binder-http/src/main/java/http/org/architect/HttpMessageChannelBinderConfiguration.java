package http.org.architect;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpMessageChannelBinderConfiguration {

//    /**
//     * 自动装配 {@link MessageReceiverController} Bean
//     *
//     * @return
//     */
//    @Bean
//    public MessageReceiverController messageReceiverController() {
//        return new MessageReceiverController();
//    }

    @Bean
    public HttpMessageChannelBinder httpMessageChannelBinder(
            DiscoveryClient discoveryClient,
            MessageReceiverController controller) {
        return new HttpMessageChannelBinder(discoveryClient, controller);
    }
}
