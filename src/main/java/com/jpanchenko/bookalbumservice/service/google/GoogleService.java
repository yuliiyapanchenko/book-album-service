package com.jpanchenko.bookalbumservice.service.google;

import com.jpanchenko.bookalbumservice.client.google.GoogleClient;
import com.jpanchenko.bookalbumservice.mapper.google.GoogleResponseMapper;
import com.jpanchenko.bookalbumservice.service.SearchService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleService implements SearchService {

    @Autowired
    @Getter
    private GoogleClient client;
    @Autowired
    @Getter
    private GoogleResponseMapper responseMapper;
}
