package com.jpanchenko.bookalbumservice.client.google;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.model.response.google.GoogleSearchResults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("google")
public class GoogleClient extends ApiClient {

    @Override
    protected Class getResponseType() {
        return GoogleSearchResults.class;
    }

    @Override
    protected String queryParam() {
        return "q";
    }
}
