package com.didispace.scca.ui.controller.ajax;

import lombok.extern.slf4j.Slf4j;
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
import java.net.URI;

/**
 * Created by stone-jin on 2018/5/28.
 */
@Controller
@Slf4j
public class SccaRestController {

    // 依据具体项目，可以修改为对应的入口url
    @RequestMapping("/xhr/**")
    @Deprecated
    public void forward(@RequestBody(required = false) String requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        long t1 = System.currentTimeMillis();
        log.info("====>");
        String result = "";
        String originalUrl = "";
        String forwardUrl = "";
        String queryString = ""; //get请求时候的参数
        try {
            //获取原始url
            originalUrl = request.getRequestURI();
            queryString = request.getQueryString();
            //去除前缀(/xhr/ius)，重构url,参数根据具体url的真是长度指定
            log.info("forwardUrl: {}", originalUrl);
            forwardUrl = originalUrl;

            URI uri = new URI("http://127.0.0.1:10030" + forwardUrl);

            forwardUrl = uri.toString();
            // 日志输出根据具体项目而定
            log.info("{}, action=REQUEST, queryString={}, forwardUrl={}, requestBody={}",
                    originalUrl, queryString, uri, requestBody);

            // 头信息统一处理
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
            RestTemplate restTemplate = new RestTemplate();

            // url统一处理
            String url = uri.toString();
            if(null != queryString) {
                url = url + "?" + queryString;
            }

            HttpEntity<String> requestEntity;
            ResponseEntity<String> responseEntity;

            switch (request.getMethod()) {
                case "GET" :
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
                    originalUrl, queryString, uri, requestBody, result, System.currentTimeMillis() - t1);

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
