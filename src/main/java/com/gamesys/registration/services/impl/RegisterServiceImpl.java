package com.gamesys.registration.services.impl;

import com.gamesys.registration.domain.Register;
import com.gamesys.registration.exception.BlackListException;
import com.gamesys.registration.exception.DuplicateRegisterException;
import com.gamesys.registration.exception.RecordNotFoundException;
import com.gamesys.registration.repositories.RegisterRepository;
import com.gamesys.registration.services.ExclusionService;
import com.gamesys.registration.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private ExclusionService exclusionService;

    @Transactional
    @Override
    public Register newRegister(Register register) {
        try {
            if (exclusionService.validate(register.getBirthDate().toString(), register.getSocialSecurityNumber())) {
                return registerRepository.save(register);
            } else {
                throw new BlackListException();
            }
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRegisterException(ex.getMessage(), ex);
        }
    }

    @Override
    public Register findById(long id) {
        return registerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public Register findBySocialSecurityNumber(String socialSecurityNumber) {
        return registerRepository.findBySocialSecurityNumber(socialSecurityNumber).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }
}
