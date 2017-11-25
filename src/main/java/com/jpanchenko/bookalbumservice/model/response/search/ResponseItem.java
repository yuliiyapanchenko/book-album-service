package com.jpanchenko.bookalbumservice.model.response.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseItem {

    private String title;
    private List<String> authors;
    private ItemType type;
}
