package com.gamesys.registration.services.impl;

import com.gamesys.registration.domain.BlackList;
import com.gamesys.registration.repositories.BlackListRepository;
import com.gamesys.registration.services.ExclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service("exclusionService")
public class ExclusionServiceImpl implements ExclusionService {

    @Autowired
    private BlackListRepository blackListRepository;

    @Cacheable(value = "blackList", key = "#birthDate + #ssn")
    @Override
    public boolean validate(String birthDate, String ssn) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE);
            Instant dtBirthDate = localDateTime.toInstant(ZoneOffset.UTC);

            Optional<BlackList> optionalBlackList = blackListRepository.findBySocialSecurityNumberAndBirthDate(ssn, dtBirthDate);

            return !optionalBlackList.isPresent();
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("The birth date need to be formatted in ISO 8601.");
        }
    }
}
