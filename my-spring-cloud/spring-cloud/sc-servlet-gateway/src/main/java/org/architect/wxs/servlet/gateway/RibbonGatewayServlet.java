package org.architect.wxs.servlet.gateway;

import com.netflix.loadbalancer.Server;
import org.architect.wxs.loadbalance.ZookeeperLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/7
 * @javadoc ：服务网关的路由规则
 * /{service-name}/{service-uri}
 */
@WebServlet(name = "ribbonGateway",urlPatterns="/ribbon/gateway/*")
public class RibbonGatewayServlet extends HttpServlet {
    @Autowired
    private ZookeeperLoadBalancer zookeeperLoadBalancer;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //${service-name}/${service-uri}
        String pathInfo = req.getPathInfo();
        String[] paths = StringUtils.split(pathInfo.substring(1),"/");
        String serviceName = paths[0];
        String serviceUri = "/"+paths[1];
        //随机选择一台实例
        Server server = chooseServer(serviceName);
        //构建目标服务uri
        String targetURL = buildTargetURL(server, serviceUri,req);
        RestTemplate restTemplate = new RestTemplate();
        // 构造 Request 实体
        RequestEntity<byte[]> requestEntity = null;
        try {
            requestEntity = createRequestEntity(req, targetURL);
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
            writeHeaders(responseEntity, resp);
            writeBody(responseEntity, resp);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    /**
     * 输出 Body 部分
     *
     * @param responseEntity
     * @param response
     * @throws IOException
     */
    private void writeBody(ResponseEntity<byte[]> responseEntity, HttpServletResponse response) throws IOException {
        if (responseEntity.hasBody()) {
            byte[] body = responseEntity.getBody();
            // 输出二进值
            ServletOutputStream outputStream = response.getOutputStream();
            // 输出 ServletOutputStream
            outputStream.write(body);
            outputStream.flush();
        }
    }
    private void writeHeaders(ResponseEntity<byte[]> responseEntity, HttpServletResponse response) {
        // 获取相应头
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        // 输出转发 Response 头
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                response.addHeader(headerName, headerValue);
            }
        }
    }

    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest req, String targetURL) throws IOException, URISyntaxException {
        byte[] body = createRequestBody(req);
        String method = req.getMethod();
        // 装换 HttpMethod
        HttpMethod httpMethod = HttpMethod.resolve(method);
        MultiValueMap<String, String> headers = createRequestHeaders(req);
        RequestEntity<byte[]> requestEntity = new RequestEntity<byte[]>(body,headers,httpMethod,new URI(targetURL));
        return requestEntity;
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }


    public byte[] createRequestBody(HttpServletRequest httpRequest) throws IOException {
        InputStream inputStream = httpRequest.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    private String buildTargetURL(Server server, String serviceUri, HttpServletRequest req) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(server.getScheme())
                .append(server.getHost())
                .append(":")
                .append(server.getPort())
                .append("/")
                .append(serviceUri);
        if(StringUtils.hasText(req.getQueryString())){
            stringBuilder.append("?").append(req.getQueryString());
        }
        return stringBuilder.toString();
    }

    private Server chooseServer(String serviceName){
        Server server = zookeeperLoadBalancer.chooseServer(serviceName);
        return server;
    }
}
