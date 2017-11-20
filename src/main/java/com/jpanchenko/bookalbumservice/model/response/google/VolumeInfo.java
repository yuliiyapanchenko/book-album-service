package com.jpanchenko.bookalbumservice.model.response.google;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {
    private String title;
    private List<String> authors;
}
