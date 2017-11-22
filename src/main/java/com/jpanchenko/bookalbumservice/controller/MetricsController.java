package com.jpanchenko.bookalbumservice.controller;

import com.jpanchenko.bookalbumservice.metrics.ResponseTimeInterceptor;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @Autowired
    ResponseTimeInterceptor responseTimeInterceptor;

    @ApiOperation(
            value = "Metrics for response times of the upstream services",
            response = Object.class)
    @ResponseBody
    @GetMapping("/statistic")
    public Object getMetrics() {
        if (responseTimeInterceptor.getMetrics().isEmpty()) {
            return "No response time metrics available yet, try to search something first.";
        }
        return responseTimeInterceptor.getMetrics();
    }
}
