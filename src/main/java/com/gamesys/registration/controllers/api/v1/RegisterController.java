package com.gamesys.registration.controllers.api.v1;

import com.gamesys.registration.domain.Register;
import com.gamesys.registration.services.RegisterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1/register")
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @ApiOperation(value = "Creates a new register.", notes = "The registers in the blacklist will be rejected.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Register register(@Valid @RequestBody Register register) {
        log.info("Create new register: {}", register);

        Register registerCreated = registerService.newRegister(register);

        log.info("Register created: {}", registerCreated);

        return registerCreated;
    }

    @ApiOperation(value = "Retrieves a register based on id.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id/{id}")
    public Register findById(@PathVariable("id") long id) {
        log.info("Find register by id: {}", id);

        Register register =  registerService.findById(id);

        log.info("Retrieving register: {}", register);

        return register;
    }

    @ApiOperation(value = "Retrieves a register based on ssn.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/ssn/{ssn}")
    public Register findById(@PathVariable("ssn") String ssn) {
        log.info("Find register by ssn: {}", ssn);

        Register register = registerService.findBySocialSecurityNumber(ssn);

        log.info("Retrieving register: {}", register);

        return register;
    }
}
