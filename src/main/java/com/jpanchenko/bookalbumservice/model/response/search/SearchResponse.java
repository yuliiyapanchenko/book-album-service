package com.jpanchenko.bookalbumservice.model.response.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel(description = "A search response with a list of books and albums")
@Data
@AllArgsConstructor
public final class SearchResponse {

    @ApiModelProperty("List of books and albums which are specify to search query.")
    private List<ResponseItem> items;
}
