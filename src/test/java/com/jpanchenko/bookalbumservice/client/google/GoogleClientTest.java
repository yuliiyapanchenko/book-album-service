package com.jpanchenko.bookalbumservice.client.google;

import com.jpanchenko.bookalbumservice.client.ApiClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GoogleClientTest extends ApiClientTest {

    private static final String MAX_RESULTS = "maxResults";
    private static final String ORDER_BY = "orderBy";
    private static final String PRINT_TYPE = "printType";

    @Autowired
    GoogleClient googleClient;

    @Test
    public void shouldLoadEndpointFromConfig() {
        assertThat(googleClient.getEndpoint(), equalTo("https://www.google.dummy.com/books/v1/volumes"));
    }

    @Test
    public void shouldLoadQueryParamsFromConfig() {
        Map<String, String> queryParams = googleClient.getQueryParams();
        verifyParam(queryParams, MAX_RESULTS, "2");
        verifyParam(queryParams, ORDER_BY, "relevance");
        verifyParam(queryParams, PRINT_TYPE, "books");
    }

    private void verifyParam(Map<String, String> queryParams, String key, String value) {
        assertTrue(queryParams.containsKey(key));
        assertThat(queryParams.get(key), equalTo(value));
    }
}