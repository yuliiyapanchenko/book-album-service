package com.jpanchenko.bookalbumservice.mapper.google;

import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.ItemType;
import com.jpanchenko.bookalbumservice.model.response.ResponseItem;
import com.jpanchenko.bookalbumservice.model.response.google.Item;
import com.jpanchenko.bookalbumservice.model.response.google.SearchResults;
import com.jpanchenko.bookalbumservice.model.response.google.VolumeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoogleResponseMapper implements ResponseMapper {

    @Override
    public List<ResponseItem> map(Object apiResults) {
        try {
            SearchResults results = (SearchResults) apiResults;
            return results.getItems().stream()
                    .map(this::mapResult)
                    .collect(Collectors.toList());
        } catch (ClassCastException e) {
            log.error("Wrong type of results", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
        }

        return Collections.emptyList();
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
