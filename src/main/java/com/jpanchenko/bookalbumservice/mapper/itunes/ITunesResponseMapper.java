package com.jpanchenko.bookalbumservice.mapper.itunes;

import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.ItemType;
import com.jpanchenko.bookalbumservice.model.response.ResponseItem;
import com.jpanchenko.bookalbumservice.model.response.itunes.Result;
import com.jpanchenko.bookalbumservice.model.response.itunes.SearchResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class ITunesResponseMapper implements ResponseMapper {

    @Override
    public List<ResponseItem> map(Object apiResults) {
        try {
            SearchResults results = (SearchResults) apiResults;
            return results.getResults().stream()
                    .map(this::mapResult)
                    .collect(toList());
        } catch (ClassCastException e) {
            log.error("Wrong type of results", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
        }
        return Collections.emptyList();
    }

    private ResponseItem mapResult(Result result) {
        return ResponseItem.builder()
                .title(result.getCollectionName())
                .authors(Collections.singletonList(result.getArtistName()))
                .type(ItemType.ALBUM)
                .build();
    }
}
