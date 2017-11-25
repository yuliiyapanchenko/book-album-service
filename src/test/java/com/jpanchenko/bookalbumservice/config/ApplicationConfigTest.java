package com.jpanchenko.bookalbumservice.config;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.jpanchenko.bookalbumservice.config.ApplicationConfig.JS_MEDIA_TYPE;
import static org.junit.Assert.assertTrue;

public class ApplicationConfigTest {

    private final ApplicationConfig config = new ApplicationConfig();

    @Test
    public void shouldCreateRestTemplateWithJsSupport() {
        RestTemplate restTemplate = config.restTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        messageConverters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .forEach(converter -> {
                    MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                    List<MediaType> supportedMediaTypes = jsonConverter.getSupportedMediaTypes();
                    assertTrue(supportedMediaTypes.contains(JS_MEDIA_TYPE));
                });
    }

}