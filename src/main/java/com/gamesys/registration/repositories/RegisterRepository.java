package com.gamesys.registration.repositories;

import com.gamesys.registration.domain.Register;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Long> {

    Optional<Register> findById(long id);

    Optional<Register> findBySocialSecurityNumber(String socialSecurityNumber);
}
