package com.jpanchenko.bookalbumservice.metrics;

import com.jpanchenko.bookalbumservice.model.response.metrics.HostMetrics;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseTimeInterceptor implements MethodInterceptor {

    private static final ConcurrentHashMap<String, HostMetrics> metricsByHost = new ConcurrentHashMap<>();

    public Map<String, HostMetrics> getMetrics() {
        return metricsByHost;
    }

    public Object invoke(MethodInvocation method) throws Throwable {
        if (Objects.isNull(method.getMethod())
                || method.getMethod().getDeclaringClass() != RestOperations.class
                || Objects.isNull(method.getArguments())
                || method.getArguments().length == 0
                || !(method.getArguments()[0] instanceof URI)) {
            return method.proceed();
        }

        String host = ((URI) method.getArguments()[0]).getHost();
        StopWatch stopWatch = new StopWatch(host);
        stopWatch.start(host);
        try {
            return method.proceed();
        } finally {
            stopWatch.stop();
            updateMetrics(host, stopWatch.getTotalTimeMillis());
        }
    }

    private void updateMetrics(String host, long elapsedTime) {
        HostMetrics hostMetrics = metricsByHost.get(host);

        if (hostMetrics == null) {
            hostMetrics = new HostMetrics(host);

            metricsByHost.put(host, hostMetrics);
        }

        long count = hostMetrics.getCount();
        hostMetrics.setCount(count + 1);
        hostMetrics.setLastTime(elapsedTime);
        long totalTime = hostMetrics.getTotalTime();
        hostMetrics.setTotalTime(totalTime + elapsedTime);
        if (elapsedTime > hostMetrics.getMaxTime() || hostMetrics.getMaxTime() == 0) {
            hostMetrics.setMaxTime(elapsedTime);
        }

        if (elapsedTime < hostMetrics.getMinTime() || hostMetrics.getMinTime() == 0) {
            hostMetrics.setMinTime(elapsedTime);
        }
    }

}
