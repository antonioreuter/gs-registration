package com.gamesys.registration.domain;

import com.gamesys.registration.validators.annotations.Password;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Data
@EqualsAndHashCode(of = {"socialSecurityNumber", "birthDate"})
@ToString(of = {"id", "username", "birthDate", "socialSecurityNumber"})
@NoArgsConstructor
public class Register implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Supports only alphanumeric characters.")
    private String username;

    @Getter(AccessLevel.NONE)
    @NotEmpty
    @Password
    private String password;

    @NotNull
    private Instant birthDate;

    @Column(unique = true)
    @NotEmpty
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "The format supported by SSN is XXX-XX-XXXX.")
    private String socialSecurityNumber;

    @Setter(AccessLevel.NONE)
    private Instant createdAt = Instant.now();
}
