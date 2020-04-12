package org.architect.wxs.event;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RemoteAppEvent} 监听器，将事件数据发送 HTTP 请求到目标机器
 * 监听 {@link ContextRefreshedEvent}
 */
public class HttpRemoteAppEventListener implements SmartApplicationListener {

    private RestTemplate restTemplate = new RestTemplate();

    // 得到 DiscoveryClient Bean
    private DiscoveryClient discoveryClient;

    public String currentAppName;

    public void onApplicationEvent(RemoteAppEvent event) {
        Object source = event.getSource();
        String appName = event.getAppName();
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);

        for (ServiceInstance s : serviceInstances) {

            String rootURL = s.isSecure() ?
                    "https://" + s.getHost() + ":" + s.getPort() :
                    "http://" + s.getHost() + ":" + s.getPort();

            String url = rootURL + "/receive/remote/event/";

            Map<String, Object> data = new HashMap<>();
            data.put("sender", currentAppName);
            data.put("value", source);
            data.put("type", RemoteAppEvent.class.getName());
            // 发送HTTP 请求到
            String responseContent = restTemplate.postForObject(url, data, String.class);

        }
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return RemoteAppEvent.class.isAssignableFrom(eventType)
                || ContextRefreshedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(@Nullable Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof RemoteAppEvent) {
            onApplicationEvent((RemoteAppEvent) event);
        } else if (event instanceof ContextRefreshedEvent) {
            onContextRefreshedEvent((ContextRefreshedEvent) event);
        }
    }

    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        this.discoveryClient = applicationContext.getBean(DiscoveryClient.class);
        this.currentAppName = applicationContext.getEnvironment().getProperty("spring.application.name");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
