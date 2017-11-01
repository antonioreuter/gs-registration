package com.gamesys.registration.repositories;

import com.gamesys.registration.domain.Register;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegisterRepositoryTest {

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void whenTryToSaveAndFindRegisterById() {
        Instant birthDate = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);
        String ssn = "180-16-1242";

        Register register = Register.builder()
                .birthDate(birthDate)
                .createdAt(Instant.now())
                .password("Test123")
                .username("testuser")
                .socialSecurityNumber(ssn).build();

        Register newRegister = registerRepository.save(register);
        Optional<Register> result = registerRepository.findById(newRegister.getId());

        assertTrue(result.isPresent());
        assertEquals(register.getSocialSecurityNumber(), result.get().getSocialSecurityNumber());
        assertEquals(register.getUsername(), result.get().getUsername());
        assertEquals(register.getBirthDate(), result.get().getBirthDate());
    }
}
