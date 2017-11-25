package com.jpanchenko.bookalbumservice.controller;

import com.jpanchenko.bookalbumservice.metrics.ResponseTimeInterceptor;
import com.jpanchenko.bookalbumservice.model.response.metrics.HostMetrics;
import com.jpanchenko.bookalbumservice.model.response.metrics.MetricsResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MetricsController {

    @Autowired
    ResponseTimeInterceptor responseTimeInterceptor;

    @ApiOperation(
            value = "Metrics for response times of the upstream services",
            response = MetricsResponse.class)
    @ResponseBody
    @GetMapping("/statistic")
    public Object getMetrics() {
        Map<String, HostMetrics> metrics = responseTimeInterceptor.getMetrics();
        if (metrics.isEmpty()) {
            return "No response time metrics available yet, try to search something first.";
        }
        return new MetricsResponse(metrics.values());
    }
}
