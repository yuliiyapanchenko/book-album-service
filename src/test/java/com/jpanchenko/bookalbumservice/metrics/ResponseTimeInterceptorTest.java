package com.jpanchenko.bookalbumservice.metrics;

import com.jpanchenko.bookalbumservice.model.response.metrics.HostMetrics;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseTimeInterceptorTest {

    @Mock
    MethodInterceptor interceptor;
    @Mock
    MethodInvocation invocation;

    private URI uri;

    @Before
    public void setUp() throws Exception {
        uri = new URI("http://test.com");
    }

    @Test
    public void shouldReturnNull() throws Throwable {

        MethodInterceptor methodInterceptor = new ResponseTimeInterceptor();
        when(interceptor.invoke(invocation)).thenReturn(null);

        assertThat(methodInterceptor.invoke(invocation), is(nullValue()));
    }

    @Test
    public void shouldCollectMetrics() throws Throwable {
        ResponseTimeInterceptor responseTimeInterceptor = new ResponseTimeInterceptor();
        ResponseEntity reference = mock(ResponseEntity.class);

        responseTimeInterceptor.invoke(
                mockInvocationOf(
                        "exchange",
                        reference,
                        URI.class,
                        HttpMethod.class,
                        HttpEntity.class,
                        Class.class)
        );

        Map<String, HostMetrics> metrics = responseTimeInterceptor.getMetrics();
        assertThat(metrics, notNullValue());
        HostMetrics actual = metrics.get("test.com");
        assertThat(actual, notNullValue());
        assertThat(actual.getCount(), is(1L));
    }

    private MethodInvocation mockInvocationOf(String methodName, Object returnValue, Class<?>... parameterTypes) throws Throwable {

        when(invocation.getMethod()).thenReturn(RestOperations.class.getMethod(methodName, parameterTypes));
        when(invocation.getArguments()).thenReturn(Collections.singletonList(uri).toArray());
        when(interceptor.invoke(invocation)).thenReturn(returnValue);

        return invocation;
    }
}