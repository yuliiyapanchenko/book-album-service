package com.jpanchenko.bookalbumservice.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ApplicationConfig {

    static final MediaType JS_MEDIA_TYPE = new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET);

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .forEach(converter -> addJsType((MappingJackson2HttpMessageConverter) converter));
        return restTemplate;
    }

    private void addJsType(MappingJackson2HttpMessageConverter converter) {
        converter.setSupportedMediaTypes(ImmutableList.of(
                MediaType.APPLICATION_JSON_UTF8,
                JS_MEDIA_TYPE)
        );
    }
}
