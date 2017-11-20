package com.jpanchenko.bookalbumservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class ApiClient {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public Optional search(String query) {
        Object responseBody = null;
        try {
            responseBody = buildAndExecuteRequest(query).getBody();
        } catch (HttpStatusCodeException error) {
            log.error("Request failed with error: " + error.getStatusCode() + " , " + error.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
        }

        return Optional.ofNullable(responseBody);
    }

    private ResponseEntity buildAndExecuteRequest(String query) {
        URI uri = getUri(query);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return restTemplate.exchange(uri, HttpMethod.GET, entity, getResponseType());
    }

    private URI getUri(String query) {
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

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    protected abstract Map<String, String> getQueryParams();

    protected abstract String queryParam();

    protected abstract String getEndpoint();

    protected abstract Class getResponseType();

}
