package com.jpanchenko.bookalbumservice.client;

import com.jpanchenko.bookalbumservice.client.google.GoogleClient;
import com.jpanchenko.bookalbumservice.model.response.google.SearchResults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApiClientTest {

    private static final String QUERY = "test";
    private static final String ENDPOINT = "https://test.com";

    @Mock
    RestOperations restOperations;

    @InjectMocks
    ApiClient apiClient = new GoogleClient();

    @Mock
    ResponseEntity responseEntity;

    @Mock
    SearchResults results;

    private HttpEntity<String> entity;
    private URI uri;


    @Before
    public void setUp() {
        apiClient.setEndpoint(ENDPOINT);
        apiClient.setQueryParams(Collections.emptyMap());
        when(responseEntity.getBody()).thenReturn(results);

        entity = new HttpEntity<>(apiClient.createHeaders());
        uri = apiClient.getUri(QUERY);
        when(restOperations.exchange(uri, HttpMethod.GET, entity, SearchResults.class)).thenReturn(responseEntity);
    }

    @Test
    public void shouldCallRestApi() {
        apiClient.search("test");
        verify(restOperations, times(1)).exchange(uri, HttpMethod.GET, entity, SearchResults.class);
    }

    @Test
    public void shouldReturnResults() {
        Optional actual = apiClient.search(QUERY);

        assertTrue(actual.isPresent());
        assertThat(actual.get(), equalTo(results));
    }

    @Test
    public void shouldReturnEmptyResultsWhenHttpStatusCodeException() {
        when(restOperations.exchange(uri, HttpMethod.GET, entity, SearchResults.class)).thenThrow(new HttpServerErrorException(NOT_FOUND));

        Optional actual = apiClient.search(QUERY);

        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldReturnEmptyResultsWhenUnexpectedException() {
        when(restOperations.exchange(uri, HttpMethod.GET, entity, SearchResults.class)).thenThrow(new NullPointerException());

        Optional actual = apiClient.search(QUERY);

        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldCreateHeaders() {
        HttpHeaders actual = apiClient.createHeaders();

        assertThat(actual.getAccept(), equalTo(Collections.singletonList(APPLICATION_JSON)));
        assertThat(actual.getContentType(), equalTo(APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldBuildUri() {
        URI uri = apiClient.getUri(QUERY);

        String actual = uri.toString();
        assertThat(actual, containsString(ENDPOINT));
        assertThat(uri.toString(), containsString(apiClient.queryParam()));
        assertThat(uri.toString(), containsString(QUERY));
    }
}