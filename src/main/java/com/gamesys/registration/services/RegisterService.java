package com.gamesys.registration.services;


import com.gamesys.registration.domain.Register;

public interface RegisterService {

    Register newRegister(Register register);

    Register findById(long id);

    Register findBySocialSecurityNumber(String socialSecurityNumber);
}
