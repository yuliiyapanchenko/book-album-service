package com.jpanchenko.bookalbumservice.client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class ApiClient {

    @Getter
    @Setter
    private String endpoint;

    @Getter
    @Setter
    private Map<String, String> queryParams;

    @Autowired
    private RestOperations restTemplate;

    public Optional search(String query) {
        Object responseBody = buildAndExecuteRequest(query).getBody();
        return Optional.ofNullable(responseBody);
    }

    private ResponseEntity buildAndExecuteRequest(String query) {
        URI uri = getUri(query);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return restTemplate.exchange(uri, HttpMethod.GET, entity, getResponseType());
    }

    URI getUri(String query) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.putAll(queryParams(query));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getEndpoint()).queryParams(queryParams);
        return builder.build().toUri();
    }

    private Map<String, List<String>> queryParams(String query) {
        MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<>();
        uriParams.setAll(getQueryParams());
        uriParams.add(queryParam(), query);
        return uriParams;
    }

    HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    protected abstract String queryParam();

    protected abstract Class getResponseType();

}
