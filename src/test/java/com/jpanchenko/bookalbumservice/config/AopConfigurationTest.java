package com.jpanchenko.bookalbumservice.config;

import com.jpanchenko.bookalbumservice.metrics.ResponseTimeInterceptor;
import org.junit.Test;
import org.springframework.aop.Advisor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AopConfigurationTest {

    private final AopConfiguration configuration = new AopConfiguration();

    @Test
    public void shouldCreatePerformanceMonitorAdvisor() {
        Advisor advisor = configuration.performanceMonitorAdvisor();
        assertThat(advisor, notNullValue());
        assertThat(advisor.getAdvice(), notNullValue());
        assertThat(advisor.getAdvice(), instanceOf(ResponseTimeInterceptor.class));
    }

    @Test
    public void shouldCreateResponseTimeInterceptor() {
        assertThat(configuration.responseTimeInterceptor(), notNullValue());
    }

}