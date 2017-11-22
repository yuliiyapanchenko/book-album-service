package com.jpanchenko.bookalbumservice.metrics;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseTimeInterceptor implements MethodInterceptor {

    private static ConcurrentHashMap<String, HostMetrics> metricsByHost = new ConcurrentHashMap<>();

    public Map<String, HostMetrics> getMetrics() {
        return metricsByHost;
    }

    public Object invoke(MethodInvocation method) throws Throwable {
        if (method.getMethod().getDeclaringClass() != RestOperations.class
                || method.getArguments().length == 0
                || !(method.getArguments()[0] instanceof URI)) {
            return null;
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

        hostMetrics.count++;
        hostMetrics.lastTime = elapsedTime;
        hostMetrics.totalTime += elapsedTime;
        if (elapsedTime > hostMetrics.maxTime || hostMetrics.maxTime == 0) {
            hostMetrics.maxTime = elapsedTime;
        }

        if (elapsedTime < hostMetrics.minTime || hostMetrics.minTime == 0) {
            hostMetrics.minTime = elapsedTime;
        }
    }

    @Data
    private class HostMetrics {
        private String host;
        private long count;
        private long totalTime;
        private long maxTime;
        private long minTime;
        private long avgTime;
        private long lastTime;

        public HostMetrics(String host) {
            this.host = host;
        }

        public long getAvgTime() {
            return totalTime / count;
        }
    }
}
