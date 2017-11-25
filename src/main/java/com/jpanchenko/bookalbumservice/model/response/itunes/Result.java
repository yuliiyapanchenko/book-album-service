package com.jpanchenko.bookalbumservice.model.response.itunes;

import lombok.Data;

@Data
public class Result {
    private String collectionType;
    private String artistName;
    private String collectionName;
}
