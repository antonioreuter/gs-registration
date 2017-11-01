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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlackListServiceImplTest {

    @Mock
    private BlackListRepository blackListRepository;

    @Spy
    @InjectMocks
    private BlackListServiceImpl blackListService;

    @Test
    public void whenTryToFindByBirthDateAndSocialSecurityNumber() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);

        BlackList record = BlackList.builder()
                .birthDate(birthDate)
                .socialSecurityNumber("180-16-1242").build();

        BlackList newRecord = BlackList.builder()
                .id(1L)
                .birthDate(birthDate)
                .socialSecurityNumber("180-16-1242").build();

        when(blackListRepository.save(record)).thenReturn(newRecord);

        BlackList result = blackListService.save(record);

        assertEquals(newRecord.getSocialSecurityNumber(), result.getSocialSecurityNumber());
        assertEquals(newRecord.getBirthDate(), result.getBirthDate());
    }

}
