package com.gamesys.registration.repositories;

import com.gamesys.registration.domain.BlackList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BlackListRepositoryTest {

    @Autowired
    private BlackListRepository blackListRepository;

    @Test
    public void whenTryToSaveANewEntry() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);

        BlackList record = BlackList.builder().birthDate(birthDate).socialSecurityNumber("180-16-1242").build();

        BlackList newRecord = blackListRepository.save(record);

        BlackList result = blackListRepository.findOne(newRecord.getId());

        assertEquals(record.getSocialSecurityNumber(), result.getSocialSecurityNumber());
    }

    @Test
    public void whenTryToFindByBirthDateAndSsn() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        BlackList record = BlackList.builder().birthDate(birthDate).socialSecurityNumber(ssn).build();

        BlackList newRecord = blackListRepository.save(record);

        Optional<BlackList> result = blackListRepository.findBySocialSecurityNumberAndBirthDate(ssn, birthDate);

        assertTrue(result.isPresent());
        assertEquals(record.getSocialSecurityNumber(), result.get().getSocialSecurityNumber());
        assertEquals(birthDate, result.get().getBirthDate());
    }
}
