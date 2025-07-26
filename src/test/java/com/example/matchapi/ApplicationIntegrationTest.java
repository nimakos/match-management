package com.example.matchapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testMatchEndpointAvailable() {
        String body = this.restTemplate.getForObject("http://localhost:" + port + "/matches", String.class);
        assertThat(body).isNotNull();
    }

    @Test
    public void testMatchOddsEndpointAvailable() {
        String body = this.restTemplate.getForObject("http://localhost:" + port + "/odds", String.class);
        assertThat(body).isNotNull();
    }
}