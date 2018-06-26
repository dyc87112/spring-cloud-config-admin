package com.didispace.scca.ui.controller.ajax;

import com.didispace.scca.ui.SccaUIProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该controller接收前端请求并转发到scca-rest服务端。
 *
 * 设置参数scca.ui.use-embed-scca-rest-server=true，开启整合部署模式：scca-rest服务端以及scca-ui端部署在一个应用中。
 * 整合部署的时候将不初始化这个controller，前端直接请求本地嵌入的scca-rest接口。
 *
 * Created by stone-jin on 2018/5/28.
 *
 * 【模块重构】 Modified by dyc87112 on 2018/06/26
 *
 */
@Slf4j
@Controller
@ConditionalOnProperty(name = "scca.ui.use-embed-scca-rest-server", havingValue = "false", matchIfMissing = true)
public class SccaRestController {

    @Autowired
    private SccaUIProperties sccaUIProperties;

    @Autowired
    private RestTemplate restTemplate;

    // 依据具体项目，可以修改为对应的入口url
    @RequestMapping("/xhr/**")
    public void forward(@RequestBody(required = false) String requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        long t1 = System.currentTimeMillis();
        log.info("====>");
        String result = "";
        String originalUrl = "";
        String queryString = ""; //get请求时候的参数

        StringBuffer forwardUrl = new StringBuffer();
        try {
            //获取前端原始请求url
            originalUrl = request.getRequestURI();
            //获取前端原始请求url中的参数
            queryString = request.getQueryString();
            log.info("originalUrl = {}, queryString = {}", originalUrl, queryString);

            // 产生要转发到rest-server后端服务的请求url
            String forwardPath = originalUrl.replace("/xhr", sccaUIProperties.getRestServerContextPath());

            if (sccaUIProperties.getRestServerName() != null) {
                // 服务发现访问rest-server
                forwardUrl.append("http://" + sccaUIProperties.getRestServerName())
                        .append(forwardPath);
            } else {
                // 配置了访问URL的方式
                forwardUrl.append(sccaUIProperties.getRestServerUrl())
                        .append(forwardPath);
            }

            // 日志输出根据具体项目而定
            log.info("{}, action=REQUEST, queryString={}, forwardUrl={}, requestBody={}",
                    originalUrl, queryString, forwardUrl, requestBody);

            // 头信息统一处理
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");

            // url统一处理
            String url = forwardUrl.toString() + "?" + queryString;

            HttpEntity<String> requestEntity;
            ResponseEntity<String> responseEntity;

            switch (request.getMethod()) {
                case "GET":
                    result = restTemplate.getForObject(url, String.class);
                    break;
                case "DELETE":
                    restTemplate.delete(url);
                    break;
                case "POST":
                    requestEntity = new HttpEntity<>(requestBody, headers);
                    responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
                    result = responseEntity.getBody();
                    break;
                case "PUT":
                    requestEntity = new HttpEntity<>(requestBody, headers);
                    responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
                    result = responseEntity.getBody();
                    break;
            }

            //  日志输出根据具体项目而定
            log.info(
                    "{}, action=SUCC, queryString={}, forwardUrl={}, requestBody={}, result={}, time={}",
                    originalUrl, queryString, url, requestBody, result, System.currentTimeMillis() - t1);

        } catch (Exception e) {
            log.error("e={}", e, e);
            //            日志输出根据具体项目而定
            log.warn("{}, action=FAIL, queryString={}, forwardUrl={}, requestBody={}, traceId={}",
                    originalUrl, queryString, forwardUrl, requestBody);
            response.setStatus(400);
            response.getWriter().print(e.getMessage());
            return;
        }
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        response.getWriter().print(result);
    }
}