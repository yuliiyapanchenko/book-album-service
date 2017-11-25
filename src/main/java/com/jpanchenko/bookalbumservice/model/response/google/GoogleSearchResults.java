package com.jpanchenko.bookalbumservice.model.response.google;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class GoogleSearchResults {
    private List<Item> items = Collections.emptyList();
}
