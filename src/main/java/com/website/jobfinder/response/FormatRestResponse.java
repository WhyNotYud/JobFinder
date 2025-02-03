package com.website.jobfinder.response;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = httpResponse.getStatus();
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatusCode(status);
        if (body instanceof String) {
            return body;
        }
        if (status >= 400) {
            // Case Errors
            return body;
        } else {
            // Case Success
            restResponse.setErrors("Call Api Success");
            restResponse.setMessage(body);
        }
        return restResponse;
    }
}
