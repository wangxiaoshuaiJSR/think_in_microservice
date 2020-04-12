package org.architect.wxs.loadbalance;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/7
 * @javadoc ：
 */
public class ZookeeperLoadBalancer extends BaseLoadBalancer {

    private final DiscoveryClient discoveryClient;
    private Map<String, BaseLoadBalancer> loadBalancerMap = new ConcurrentHashMap<>();

    public ZookeeperLoadBalancer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        //更新所有的service
        updateServers();
    }

    @Override
    public Server chooseServer(Object key) {
        if (key instanceof String) {
            String serviceName = String.valueOf(key);
            BaseLoadBalancer baseLoadBalancer = loadBalancerMap.get(serviceName);
            RoundRobinRule roundRobinRule = new RoundRobinRule(baseLoadBalancer);
            return roundRobinRule.choose(serviceName);
        }
        return super.chooseServer(key);
    }


    /**
     * 更新所有服务器
     */
    @Scheduled(fixedRate = 5000)
    public void updateServers() {
        Map<String, BaseLoadBalancer> oldMap = new HashMap<>();
        discoveryClient.getServices().stream().forEach(serviceName->{
            BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
            discoveryClient.getInstances(serviceName).forEach(serviceInstance -> {
                Server server = new Server(serviceInstance.isSecure()?"https://":"http://",serviceInstance.getHost(),
                        serviceInstance.getPort());
                loadBalancer.addServer(server);
            });
            oldMap.put(serviceName,loadBalancer);
        });
        loadBalancerMap.putAll(oldMap);
    }


}
