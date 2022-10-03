package com.easy.trip.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Steven
 * @date 2022年10月04日 2:05
 */
public class HttpUtil {


    private RestTemplate restTemplate = null;

    public HttpUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(maxAttempts = 4, include = RestClientException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
    public <T> ResponseEntity post(String url, HttpHeaders httpHeaders, Class<?> classZ) {
        return restTemplate.postForEntity(url, httpHeaders, classZ);
    }


    @Retryable(maxAttempts = 4, include = RestClientException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
    public <T> ResponseEntity get(String url, Class<?> classZ) {
        return restTemplate.getForEntity(url, classZ);
    }

    @Retryable(maxAttempts = 4, include = RestClientException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
    public <T> ResponseEntity get(String url, Class<?> classZ, Map<String, String> map) {
        return restTemplate.getForEntity(url, classZ, map);
    }

    @Retryable(maxAttempts = 4, include = RestClientException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
    public <T> ResponseEntity get(String url, HttpHeaders httpHeaders, Class<?> classZ, Map<String, String> map) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), classZ, map);
    }
    

}
