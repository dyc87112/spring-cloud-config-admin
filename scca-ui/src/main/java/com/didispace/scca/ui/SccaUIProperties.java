package com.didispace.scca.ui;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scca.ui")
public class SccaUIProperties {

    /**
     * scca-rest服务端的访问URL
     */
    private String restServerUrl = "http://localhost:10030/";
    /**
     * scca-rest服务端在服务发现机制下的服务名（需要与@EnableDiscoveryClient配合使用，可以加入consul、eureka或其他服务治理机制）
     */
    private String restServerName = null;
    /**
     * scca-rest服务端的contextPath
     */
    private String restServerContextPath = "";
    /**
     *
     */
    private boolean useEmbedSccaRestServer;

}
