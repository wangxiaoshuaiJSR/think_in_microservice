package http.org.architect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class HttpMessageChannelWebAutoConfiguration {

    @Bean
    public MessageReceiverController controller() {
        return new MessageReceiverController();
    }
//
//    @Bean
//    public MessageReceiverHandlerInterceptor interceptor() {
//        return new MessageReceiverHandlerInterceptor();
//    }
//
//    @Autowired
//    private MessageReceiverHandlerInterceptor interceptor;

//    @Configuration
//    public class MyWebMvcConfigurer implements WebMvcConfigurer {
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(interceptor);
//        }
//    }


}
