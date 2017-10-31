package com.gamesys.registration.repositories;


import com.gamesys.registration.domain.BlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList, Long> {

    Optional<BlackList> findBySocialSecurityNumberAndBirthDate(String ssn, Instant birthDate);
}
