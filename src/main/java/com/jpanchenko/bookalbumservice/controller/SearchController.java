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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<List<ResponseItem>>> callables = searchServices.stream()
                .map(service -> new ServiceCallable(query, service))
                .collect(toList());

        try {
            return executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .flatMap(Collection::stream)
                    .sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()))
                    .collect(toList());
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
        }
        return Collections.emptyList();
    }

    private class ServiceCallable implements Callable<List<ResponseItem>> {

        private final String query;
        private final SearchService service;

        private ServiceCallable(String query, SearchService service) {
            this.query = query;
            this.service = service;
        }

        @Override
        public List<ResponseItem> call() throws Exception {
            return service.search(query);
        }
    }
}
