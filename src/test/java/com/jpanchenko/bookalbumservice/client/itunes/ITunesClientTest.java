package com.jpanchenko.bookalbumservice.client.itunes;

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
public class ITunesClientTest {

    private static final String LIMIT = "limit";
    private static final String ATTRIBUTE = "attribute";
    private static final String ENTITY = "entity";

    @Autowired
    ITunesClient iTunesClient;

    @Test
    public void shouldLoadEndpointFromConfig() {
        assertThat(iTunesClient.getEndpoint(), equalTo("https://itunes.dummy.apple.com/search"));
    }

    @Test
    public void shouldLoadQueryParamsFromConfig() {
        Map<String, String> queryParams = iTunesClient.getQueryParams();
        verifyParam(queryParams, LIMIT, "3");
        verifyParam(queryParams, ATTRIBUTE, "albumTerm");
        verifyParam(queryParams, ENTITY, "album");
    }

    private void verifyParam(Map<String, String> queryParams, String key, String value) {
        assertTrue(queryParams.containsKey(key));
        assertThat(queryParams.get(key), equalTo(value));
    }

}