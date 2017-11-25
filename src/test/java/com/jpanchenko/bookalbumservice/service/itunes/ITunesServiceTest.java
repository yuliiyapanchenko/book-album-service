package com.jpanchenko.bookalbumservice.service.itunes;

import com.jpanchenko.bookalbumservice.client.ApiClient;
import com.jpanchenko.bookalbumservice.client.itunes.ITunesClient;
import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.mapper.itunes.ITunesResponseMapper;
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
public class ITunesServiceTest {

    @Autowired
    ITunesService iTunesService;

    @Test
    public void shouldInjectItunesClient() {
        ApiClient client = iTunesService.getClient();
        assertThat(client, notNullValue());
        assertThat(client, instanceOf(ITunesClient.class));
    }

    @Test
    public void shouldInjectItunesResponseMapper() {
        ResponseMapper mapper = iTunesService.getResponseMapper();
        assertThat(mapper, notNullValue());
        assertThat(mapper, instanceOf(ITunesResponseMapper.class));
    }
}