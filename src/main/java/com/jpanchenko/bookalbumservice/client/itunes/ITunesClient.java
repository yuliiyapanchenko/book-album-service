package com.jpanchenko.bookalbumservice.client.itunes;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.model.response.itunes.ITunesSearchResults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("itunes")
public class ITunesClient extends ApiClient {

    @Override
    protected Class getResponseType() {
        return ITunesSearchResults.class;
    }

    @Override
    protected String queryParam() {
        return "term";
    }
}
