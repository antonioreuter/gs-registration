package com.gamesys.registration.controllers.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.registration.domain.Register;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Test
    public void whenTryToCreateANewRegister() throws Exception {
        String payload = "{ \"username\": \"user\", \"password\": \"Test123\", \"socialSecurityNumber\": \"180-16-1242\", \"birthDate\": \"1980-07-08T00:00:00Z\"}";
        HttpEntity<String> entity = new HttpEntity<>(payload, customHeaders());

        String url = "http://localhost:"+port+"/api/v1/register";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Register result = objectMapper.readValue(response.getBody(), Register.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(result.getId());
        assertEquals("180-16-1242", result.getSocialSecurityNumber());
        assertEquals("user", result.getUsername());
        assertEquals("1980-07-08T00:00:00Z", result.getBirthDate().toString());
    }

    private HttpHeaders customHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    @TestConfiguration
    static class Config {
        @Value("${api.username}")
        private String username;

        @Value("${api.password}")
        private String password;

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .basicAuthorization(username, password);
        }
    }
}
