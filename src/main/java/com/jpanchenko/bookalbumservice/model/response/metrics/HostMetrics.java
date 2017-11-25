package com.jpanchenko.bookalbumservice.model.response.metrics;

import lombok.Data;

@Data
public class HostMetrics {
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
