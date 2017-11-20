package com.jpanchenko.bookalbumservice.client.google;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.model.response.google.SearchResults;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("google")
public class GoogleClient extends ApiClient {
    @Getter
    @Setter
    private String endpoint;

    @Getter
    @Setter
    private Map<String, String> queryParams;

    @Override
    protected Class getResponseType() {
        return SearchResults.class;
    }

    @Override
    protected String queryParam() {
        return "q";
    }
}
