package com.didispace.scca.rest.config;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

public class SccaErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
                                                  boolean includeStackTrace) {
        Map<String, Object> body = super.getErrorAttributes(requestAttributes, includeStackTrace);
        body.put("code", body.get("status"));
        body.remove("status");
        return body;
    }

}