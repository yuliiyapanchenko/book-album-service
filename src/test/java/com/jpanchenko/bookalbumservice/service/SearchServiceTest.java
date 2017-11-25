package com.jpanchenko.bookalbumservice.service;

import com.jpanchenko.bookalbumservice.client.itunes.ITunesClient;
import com.jpanchenko.bookalbumservice.mapper.itunes.ITunesResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.itunes.ITunesSearchResults;
import com.jpanchenko.bookalbumservice.model.response.search.ResponseItem;
import com.jpanchenko.bookalbumservice.service.itunes.ITunesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {
    private final String query = "test";

    @Mock
    private ITunesClient client;
    @Mock
    private ITunesResponseMapper responseMapper;
    @Mock
    ITunesSearchResults results;

    @InjectMocks
    private ITunesService service;

    @Before
    public void setUp() {
        when(client.search(query)).thenReturn(Optional.of(results));
        when(responseMapper.map(results)).thenReturn(Collections.emptyList());
    }

    @Test
    public void shouldCallClientAndParseResults() throws Exception {

        CompletableFuture<List<ResponseItem>> future = service.search(query);
        List<ResponseItem> responseItems = future.get();

        verify(client, times(1)).search(query);
        verify(responseMapper, times(1)).map(any(ITunesSearchResults.class));
    }
}