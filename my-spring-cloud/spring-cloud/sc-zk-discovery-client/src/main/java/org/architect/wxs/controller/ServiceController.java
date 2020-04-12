package org.architect.wxs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/5
 * @javadoc ：
 */
@RestController
public class ServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/services")
    public List<String> getAllService(){
        return discoveryClient.getServices();
    }

    @GetMapping("/service/instance/{serviceName}")
    public List<String> getAllServiceInstance(@PathVariable String serviceName){
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(s->s.getServiceId()+"-"+s.getHost()+":"+s.getPort()).collect(Collectors.toList());
    }
}
