package com.jpanchenko.bookalbumservice.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket salesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("book-album-service")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.or(PathSelectors.regex("/health.*"), PathSelectors.regex("/search.*"), PathSelectors.regex("/statistic.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Service for searching books and albums")
                .description("Simple service for searching books and albums via Google Books APIs and iTunes Search API")
                .version("1.0")
                .build();
    }
}
