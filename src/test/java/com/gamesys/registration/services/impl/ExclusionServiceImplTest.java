package com.gamesys.registration.services.impl;

import com.gamesys.registration.domain.BlackList;
import com.gamesys.registration.repositories.BlackListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExclusionServiceImplTest {

    @Mock
    private BlackListRepository blackListRepository;

    @Spy
    @InjectMocks
    private ExclusionServiceImpl exclusionService;

    @Test
    public void whenTryToValidateAndRecordIsNotOnBlackList() {
        String ssn = "180-16-1242";
        Instant dtBirthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);

        when(blackListRepository.findBySocialSecurityNumberAndBirthDate(ssn, dtBirthDate)).thenReturn(Optional.empty());

        boolean result = exclusionService.validate(dtBirthDate.toString(), ssn);

        assertTrue(result);
    }

    @Test
    public void whenTryToValidateAndRecordIsOnBlackList() {
        String ssn = "180-16-1242";
        Instant dtBirthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        BlackList blackList = BlackList.builder()
                .birthDate(dtBirthDate)
                .socialSecurityNumber(ssn).build();


        when(blackListRepository.findBySocialSecurityNumberAndBirthDate(ssn, dtBirthDate)).thenReturn(Optional.of(blackList));

        boolean result = exclusionService.validate(dtBirthDate.toString(), ssn);

        assertFalse(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTryToValidateAndDateIsInvalid() {
        String ssn = "180-16-1242";
        String birthDate = "1980/07/01T00:00:00Z";

        boolean result = exclusionService.validate(birthDate, ssn);

        verifyZeroInteractions(blackListRepository);
    }
}
