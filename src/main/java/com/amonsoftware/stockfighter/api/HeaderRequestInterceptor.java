package com.amonsoftware.stockfighter.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    public static final String X_STARFIGHTER_AUTHORIZATION_HEADER = "X-Starfighter-Authorization";
    private String apiKey;

    public HeaderRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(httpRequest);
        wrapper.getHeaders().add(X_STARFIGHTER_AUTHORIZATION_HEADER, apiKey);
        wrapper.getHeaders().add(HttpHeaders.ACCEPT, "application/json");
        wrapper.getHeaders().add("Cookie", apiKey);
        return clientHttpRequestExecution.execute(wrapper, bytes);
    }
}
