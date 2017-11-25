package com.jpanchenko.bookalbumservice.mapper.itunes;

import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.itunes.ITunesSearchResults;
import com.jpanchenko.bookalbumservice.model.response.search.ResponseItem;
import com.jpanchenko.bookalbumservice.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jpanchenko.bookalbumservice.model.response.search.ItemType.ALBUM;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class ITunesResponseMapperTest {

    private final ResponseMapper mapper = new ITunesResponseMapper();

    private ITunesSearchResults results;

    @Before
    public void setUp() {
        results = TestUtil.toInternalObject("itunes_rs.json", this.getClass(), ITunesSearchResults.class);
    }

    @Test
    public void shouldMapItunesSearchResults() {
        List<ResponseItem> actual = mapper.map(results);
        assertThat(actual, not(empty()));
        actual.forEach(item -> {
            assertThat(item, notNullValue());
            assertThat(item.getTitle(), not(isEmptyOrNullString()));
            assertThat(item.getAuthors(), not(empty()));
            item.getAuthors().forEach(author -> assertThat(author, not(isEmptyOrNullString())));
            assertThat(item.getType(), equalTo(ALBUM));
        });
    }
}