package com.jpanchenko.bookalbumservice.controller;

import com.jpanchenko.bookalbumservice.metrics.ResponseTimeInterceptor;
import com.jpanchenko.bookalbumservice.model.response.metrics.HostMetrics;
import com.jpanchenko.bookalbumservice.model.response.metrics.MetricsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricsControllerTest {

    @Mock
    ResponseTimeInterceptor responseTimeInterceptor;

    @Mock
    HostMetrics metrics;

    @InjectMocks
    MetricsController metricsController;

    @Test
    public void shouldReturnMetrics() {
        Map<String, HostMetrics> metricsMap = new HashMap<>();
        metricsMap.put("test", metrics);
        when(responseTimeInterceptor.getMetrics()).thenReturn(metricsMap);

        Object actualRs = metricsController.getMetrics();

        verify(responseTimeInterceptor, times(1)).getMetrics();
        assertThat(actualRs, notNullValue());
        assertThat(actualRs, instanceOf(MetricsResponse.class));

        Collection<HostMetrics> actualMetrics = ((MetricsResponse) actualRs).getMetrics();
        assertThat(actualMetrics, not(empty()));
        assertThat(actualMetrics, contains(metrics));
    }

    @Test
    public void shouldReturnMessageWhenNoMetricsAvailable() {
        when(responseTimeInterceptor.getMetrics()).thenReturn(Collections.emptyMap());

        Object actualRs = metricsController.getMetrics();

        verify(responseTimeInterceptor, times(1)).getMetrics();
        assertThat(actualRs, notNullValue());

        String actualMsg = (String) actualRs;
        assertThat(actualMsg, not(isEmptyString()));
    }
}