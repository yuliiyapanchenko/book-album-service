package com.jpanchenko.bookalbumservice.model.response.itunes;

import lombok.Data;

import java.util.List;

@Data
public class SearchResults {
    private List<Result> results;
}
