package com.jpanchenko.bookalbumservice.model.response.google;

import lombok.Data;

import java.util.List;

@Data
public class SearchResults {
    private List<Item> items;
}
