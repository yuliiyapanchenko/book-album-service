package com.jpanchenko.bookalbumservice.controller;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.ResponseItem;
import com.jpanchenko.bookalbumservice.model.response.SearchResponse;
import com.jpanchenko.bookalbumservice.service.SearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

    private SearchController searchController;

    @Mock
    SearchService service1;

    @Mock
    SearchService service2;

    @Mock
    ResponseItem responseItem1;

    @Mock
    ResponseItem responseItem2;

    @Before
    public void setUp() throws Exception {

        when(responseItem1.getTitle()).thenReturn("A");
        when(responseItem2.getTitle()).thenReturn("B");

        List<ResponseItem> items1 = Collections.singletonList(responseItem1);
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> items1);
        when(service1.search(anyString())).thenReturn(future1);

        List<ResponseItem> items2 = Collections.singletonList(responseItem2);
        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> items2);
        when(service2.search(anyString())).thenReturn(future2);

        searchController = new SearchController(Arrays.asList(service1, service2));
    }

    @Test
    public void shouldMergeServicesResults() {
        SearchResponse expected = new SearchResponse(Arrays.asList(responseItem1, responseItem2));
        SearchResponse actual = searchController.search("test");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldSortResultsByTitleAlphabetically() {
        when(responseItem1.getTitle()).thenReturn("B");
        when(responseItem2.getTitle()).thenReturn("A");

        SearchResponse expected = new SearchResponse(Arrays.asList(responseItem2, responseItem1));
        SearchResponse actual = searchController.search("test");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldFinishTimeout() throws Exception {
        searchController = new SearchController(Collections.singletonList(new SearchServiceMock()));
        searchController.setTimeout(1);

        SearchResponse expected = new SearchResponse(Collections.emptyList());
        SearchResponse actual = searchController.search("test");
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldHandleException() {
        when(service1.search(anyString())).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        }));
        SearchResponse expected = new SearchResponse(Collections.singletonList(responseItem2));

        SearchResponse actual = searchController.search("test");

        assertThat(actual, equalTo(expected));
    }

    private class SearchServiceMock implements SearchService {
        @Override
        public CompletableFuture<List<ResponseItem>> search(String query) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Arrays.asList(responseItem1, responseItem2);
            });
        }

        @Override
        public ResponseMapper getResponseMapper() {
            return null;
        }

        @Override
        public ApiClient getClient() {
            return null;
        }
    }

}