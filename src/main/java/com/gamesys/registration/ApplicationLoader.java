package com.gamesys.registration;

import com.gamesys.registration.domain.BlackList;
import com.gamesys.registration.services.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class ApplicationLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BlackListService blackListService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Instant birthDate01 = LocalDateTime.of(1980, 07, 01, 00, 00).toInstant(ZoneOffset.UTC);

        Instant birthDate02 = LocalDateTime.of(2000, 10, 05, 00, 00).toInstant(ZoneOffset.UTC);

        Instant birthDate03 = LocalDateTime.of(1982, 03, 15, 00, 00).toInstant(ZoneOffset.UTC);

        Instant birthDate04 = LocalDateTime.of(1970, 01, 01, 00, 00).toInstant(ZoneOffset.UTC);

        Instant birthDate05 = LocalDateTime.of(1995, 10, 30, 00, 00).toInstant(ZoneOffset.UTC);


        BlackList blackList01 = BlackList.builder().birthDate(birthDate01).socialSecurityNumber("180-16-1242").build();
        blackListService.save(blackList01);

        BlackList blackList02 = BlackList.builder().birthDate(birthDate02).socialSecurityNumber("601-68-2719").build();
        blackListService.save(blackList02);

        BlackList blackList03 = BlackList.builder().birthDate(birthDate03).socialSecurityNumber("574-55-1434").build();
        blackListService.save(blackList03);

        BlackList blackList04 = BlackList.builder().birthDate(birthDate04).socialSecurityNumber("675-38-6091").build();
        blackListService.save(blackList04);

        BlackList blackList05 = BlackList.builder().birthDate(birthDate05).socialSecurityNumber("222-34-2922").build();
        blackListService.save(blackList05);
    }
}
