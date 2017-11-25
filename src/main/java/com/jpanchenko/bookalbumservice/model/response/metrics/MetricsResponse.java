package com.jpanchenko.bookalbumservice.model.response.metrics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;

@ApiModel(description = "A statistics response with a list of time metrics for responses of upstream services")
@Data
public class MetricsResponse {

    @ApiModelProperty("List of time metrics for upstream services")
    private final Collection<HostMetrics> metrics;

    public MetricsResponse(Collection<HostMetrics> metrics) {
        this.metrics = metrics;
    }
}
