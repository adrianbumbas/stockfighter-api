package com.amonsoftware.stockfighter.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final String API_KEY = "6c0f27076afa7582a81541544105c0f6b4b055a5";
    public static final String X_STARFIGHTER_AUTHORIZATION = "X-Starfighter-Authorization";

    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(httpRequest);
        wrapper.getHeaders().add(X_STARFIGHTER_AUTHORIZATION, API_KEY);
        wrapper.getHeaders().add(HttpHeaders.ACCEPT, "application/json");
        wrapper.getHeaders().add("Cookie",API_KEY);
        return clientHttpRequestExecution.execute(wrapper, bytes);
    }
}
