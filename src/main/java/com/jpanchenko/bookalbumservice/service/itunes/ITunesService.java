package com.jpanchenko.bookalbumservice.service.itunes;

import com.jpanchenko.bookalbumservice.client.itunes.ITunesClient;
import com.jpanchenko.bookalbumservice.mapper.itunes.ITunesResponseMapper;
import com.jpanchenko.bookalbumservice.service.SearchService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITunesService implements SearchService {

    @Autowired
    @Getter
    private ITunesClient client;
    @Autowired
    @Getter
    private ITunesResponseMapper responseMapper;
}
