package com.jpanchenko.bookalbumservice.service.google;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.client.google.GoogleClient;
import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.mapper.google.GoogleResponseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GoogleServiceTest {

    @Autowired
    GoogleService googleService;

    @Test
    public void shouldInjectGoogleClient() {
        ApiClient client = googleService.getClient();
        assertThat(client, notNullValue());
        assertThat(client, instanceOf(GoogleClient.class));
    }

    @Test
    public void shouldInjectGoogleResponseMapper() {
        ResponseMapper mapper = googleService.getResponseMapper();
        assertThat(mapper, notNullValue());
        assertThat(mapper, instanceOf(GoogleResponseMapper.class));
    }

}