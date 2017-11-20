package com.jpanchenko.bookalbumservice.service;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.ResponseItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface SearchService {

    default List<ResponseItem> search(String query) {
        Optional results = getClient().search(query);
        return results.isPresent() ? getResponseMapper().map(results.get()) : Collections.emptyList();
    }

    ResponseMapper getResponseMapper();

    ApiClient getClient();
}
