package com.jpanchenko.bookalbumservice.mapper;

import com.jpanchenko.bookalbumservice.model.response.search.ResponseItem;

import java.util.List;

public interface ResponseMapper {

    List<ResponseItem> map(Object apiResults);
}
