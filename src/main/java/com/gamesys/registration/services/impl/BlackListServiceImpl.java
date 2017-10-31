package com.gamesys.registration.services.impl;

import com.gamesys.registration.domain.BlackList;
import com.gamesys.registration.exception.RecordNotFoundException;
import com.gamesys.registration.repositories.BlackListRepository;
import com.gamesys.registration.services.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service("blackListService")
public class BlackListServiceImpl implements BlackListService {

    @Autowired
    private BlackListRepository blackListRepository;

    @Transactional
    @Override
    public BlackList save(BlackList blackList) {
        return blackListRepository.save(blackList);
    }

}
