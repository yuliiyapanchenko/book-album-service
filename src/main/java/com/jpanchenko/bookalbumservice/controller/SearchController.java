package com.jpanchenko.bookalbumservice.controller;

import com.jpanchenko.bookalbumservice.model.response.ResponseItem;
import com.jpanchenko.bookalbumservice.model.response.SearchResponse;
import com.jpanchenko.bookalbumservice.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RestController("/")
public class SearchController {

    @Autowired
    List<SearchService> searchServices;

    @ApiOperation(
            value = "Search for books albums by specified query",
            response = SearchResponse.class)
    @ResponseBody
    @GetMapping(value = "/search", produces = APPLICATION_JSON_UTF8_VALUE)
    public SearchResponse search(@ApiParam(value = "Text to search", required = true) @RequestParam("query") String query) {
        List<ResponseItem> results = executeServicesInParallel(query);
        return new SearchResponse(results);
    }

    private List<ResponseItem> executeServicesInParallel(String query) {
        List<CompletableFuture<List<ResponseItem>>> futures = searchServices.stream()
                .map(service -> CompletableFuture.supplyAsync(() -> service.search(query))
                        .exceptionally(ex -> {
                            log.error("Unexpected error occurred", ex);
                            return Collections.emptyList();
                        }))
                .collect(toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()))
                .collect(toList());
    }
}
