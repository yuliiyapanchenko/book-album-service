package com.jpanchenko.bookalbumservice.mapper.google;

import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.google.GoogleSearchResults;
import com.jpanchenko.bookalbumservice.model.response.google.Item;
import com.jpanchenko.bookalbumservice.model.response.google.VolumeInfo;
import com.jpanchenko.bookalbumservice.model.response.search.ItemType;
import com.jpanchenko.bookalbumservice.model.response.search.ResponseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoogleResponseMapper implements ResponseMapper {

    @Override
    public List<ResponseItem> map(Object apiResults) {
        GoogleSearchResults results = (GoogleSearchResults) apiResults;
        return results.getItems().stream()
                .map(this::mapResult)
                .collect(Collectors.toList());
    }

    private ResponseItem mapResult(Item item) {
        VolumeInfo info = item.getVolumeInfo();
        return ResponseItem.builder()
                .title(info.getTitle())
                .authors(info.getAuthors())
                .type(ItemType.BOOK)
                .build();
    }
}
