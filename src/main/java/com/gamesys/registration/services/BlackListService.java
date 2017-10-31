package com.gamesys.registration.services;

import com.gamesys.registration.domain.BlackList;

import java.time.Instant;

public interface BlackListService {

    BlackList save(BlackList blackList);

}
