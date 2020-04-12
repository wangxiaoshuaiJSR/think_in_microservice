package org.architect.wxs.loadbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class LoadBalancedRequestInterceptor implements ClientHttpRequestInterceptor {

    // Map Key service Name , Value URLs
    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedRate = 10 * 1000) // 10 秒钟更新一次缓存
    public void updateTargetUrlsCache() { // 更新目标 URLs
        // 获取当前应用的机器列表
        // http://${ip}:${port}
        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            Set<String> newTargetUrls = serviceInstances
                    .stream()
                    .map(s ->
                            s.isSecure() ?
                                    "https://" + s.getHost() + ":" + s.getPort() :
                                    "http://" + s.getHost() + ":" + s.getPort()
                    ).collect(Collectors.toSet());
            newTargetUrlsCache.put(serviceName, newTargetUrls);
        });

        this.targetUrlsCache = newTargetUrlsCache;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //  URI:  "/" + serviceName + "/say?message="
        URI requestURI = request.getURI();
        String path = requestURI.getPath();
        String[] parts = StringUtils.split(path.substring(1), "/");
        String serviceName = parts[0]; // serviceName
        String uri = parts[1];  // "/say?message="
        // 服务器列表快照
        List<String> targetUrls = new LinkedList<>(targetUrlsCache.get(serviceName));
        int size = targetUrls.size();
        // size =3 , index =0 -2
        int index = new Random().nextInt(size);
        // 选择其中一台服务器
        String targetURL = targetUrls.get(index);
        // 最终服务器 URL
        String actualURL = targetURL + "/" + uri + "?" + requestURI.getQuery();
        // 执行请求

        System.out.println("本次请求的 URL : " + actualURL);

        URL url = new URL(actualURL);

        URLConnection urlConnection = url.openConnection();

        // 响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 响应主体
        InputStream responseBody = urlConnection.getInputStream();

        return new SimpleClientHttpResponse(httpHeaders, responseBody);
    }

    private static class SimpleClientHttpResponse implements ClientHttpResponse {

        private HttpHeaders headers;

        private InputStream body;

        public SimpleClientHttpResponse(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }


}
