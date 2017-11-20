package com.jpanchenko.bookalbumservice.model.response.google;

import lombok.Data;

@Data
public class Item {
    private String kind;
    private VolumeInfo volumeInfo;
}
