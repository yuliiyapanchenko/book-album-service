package com.jpanchenko.bookalbumservice.mapper.google;

import com.jpanchenko.bookalbumservice.mapper.ResponseMapper;
import com.jpanchenko.bookalbumservice.model.response.google.GoogleSearchResults;
import com.jpanchenko.bookalbumservice.model.response.search.ResponseItem;
import com.jpanchenko.bookalbumservice.util.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jpanchenko.bookalbumservice.model.response.search.ItemType.BOOK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class GoogleResponseMapperTest {

    private final ResponseMapper mapper = new GoogleResponseMapper();

    private GoogleSearchResults results;

    @Before
    public void setUp() {
        results = TestUtil.toInternalObject("google_rs.json", this.getClass(), GoogleSearchResults.class);
    }

    @Test
    public void shouldMapGoogleSearchResults() {
        List<ResponseItem> actual = mapper.map(results);
        assertThat(actual, not(empty()));
        actual.forEach(item -> {
            assertThat(item, notNullValue());
            assertThat(item.getTitle(), not(isEmptyOrNullString()));
            assertThat(item.getAuthors(), not(empty()));
            item.getAuthors().forEach(author -> assertThat(author, not(isEmptyOrNullString())));
            assertThat(item.getType(), equalTo(BOOK));
        });
    }
}