package com.gamesys.registration.services.impl;

import com.gamesys.registration.domain.Register;
import com.gamesys.registration.exception.BlackListException;
import com.gamesys.registration.exception.DuplicateRegisterException;
import com.gamesys.registration.exception.RecordNotFoundException;
import com.gamesys.registration.repositories.RegisterRepository;
import com.gamesys.registration.services.ExclusionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {


    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private ExclusionService exclusionService;

    @Spy
    @InjectMocks
    private RegisterServiceImpl registerService;

    @Test
    public void whenTryToRegisterNewRecord() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        Register register = Register.builder()
                .id(1L)
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        when(exclusionService.validate(anyString(), anyString())).thenReturn(true);
        when(registerRepository.save(any(Register.class))).thenReturn(register);

        Register result = registerService.newRegister(register);

        assertEquals(register.getBirthDate(), result.getBirthDate());
        assertEquals(register.getSocialSecurityNumber(), result.getSocialSecurityNumber());
        assertEquals("testuser", result.getUsername());
    }

    @Test(expected = BlackListException.class)
    public void whenTryToRegisterNewRecordThatIsOnBlackList() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        Register register = Register.builder()
                .id(1L)
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        when(exclusionService.validate(anyString(), anyString())).thenReturn(false);

        registerService.newRegister(register);

        verifyZeroInteractions(registerRepository);
    }

    @Test(expected = DuplicateRegisterException.class)
    public void whenTryToRegisterNewRecordThatAlreadyExists() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        Register register = Register.builder()
                .id(1L)
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        when(exclusionService.validate(anyString(), anyString())).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(registerRepository).save(any(Register.class));

        registerService.newRegister(register);
    }

    @Test
    public void whenTryToFindById() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        long registerId = 1L;
        Register register = Register.builder()
                .id(registerId)
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        when(registerRepository.findById(registerId)).thenReturn(Optional.of(register));

        Register result = registerService.findById(registerId);

        assertEquals(register.getBirthDate(), result.getBirthDate());
        assertEquals(register.getSocialSecurityNumber(), result.getSocialSecurityNumber());
        assertEquals("testuser", result.getUsername());
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenTryToFindByIdAndRecordNotFound() {
        long registerId = 1L;

        when(registerRepository.findById(registerId)).thenReturn(Optional.empty());

        registerService.findById(registerId);
    }

    @Test
    public void whenTryToFindBySsn() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        long registerId = 1L;
        Register register = Register.builder()
                .id(registerId)
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        when(registerRepository.findBySocialSecurityNumber(ssn)).thenReturn(Optional.of(register));

        Register result = registerService.findBySocialSecurityNumber(ssn);

        assertEquals(register.getBirthDate(), result.getBirthDate());
        assertEquals(register.getSocialSecurityNumber(), result.getSocialSecurityNumber());
        assertEquals("testuser", result.getUsername());
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenTryToFindBySsnAndRecordNotFound() {
        String ssn = "180-16-1242";

        when(registerRepository.findBySocialSecurityNumber(ssn)).thenReturn(Optional.empty());

        registerService.findBySocialSecurityNumber(ssn);
    }
}
