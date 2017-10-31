package com.gamesys.registration.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


@Entity
@Table(name = "blacklist", indexes = {
        @Index(name = "IX_SSN", unique = true, columnList = "birthDate, socialSecurityNumber")
})
@Data
@Builder
@EqualsAndHashCode(of = {"socialSecurityNumber", "birthDate"})
@ToString(of = {"id", "birthDate", "socialSecurityNumber"})
@NoArgsConstructor
@AllArgsConstructor
public class BlackList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Instant birthDate;

    private String socialSecurityNumber;
}
