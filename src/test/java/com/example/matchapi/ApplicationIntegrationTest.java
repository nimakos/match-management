package com.example.matchapi;

import com.example.matchapi.entities.Match;
import com.example.matchapi.entities.MatchOdds;
import com.example.matchapi.enums.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.matchapi.mappings.Mappings.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    public void testMatchEndpointAvailable() {
        String body = this.restTemplate.getForObject(url(MATCHES), String.class);
        assertThat(body).isNotNull();
    }

    @Test
    public void testMatchOddsEndpointAvailable() {
        String body = this.restTemplate.getForObject(url(ODDS), String.class);
        assertThat(body).isNotNull();
    }

    @Test
    public void testGetAllMatches() {
        ResponseEntity<String> response = restTemplate.getForEntity(url(MATCHES + GET_MATCHES), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateAndGetMatch() {
        Match match = new Match();
        match.setTeam_a("Alpha");
        match.setTeam_b("Beta");
        match.setSport(Sport.FOOTBALL);
        match.setMatchDate(LocalDate.of(2025, 12, 1));

        ResponseEntity<Match> createResp = restTemplate.postForEntity(url(MATCHES + CREATE_MATCH), match, Match.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long matchId = createResp.getBody().getId();

        ResponseEntity<Match> getResp = restTemplate.getForEntity(url(MATCHES + GET_MATCH.replace("{id}", String.valueOf(matchId))), Match.class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody().getTeam_a()).isEqualTo("Alpha");
    }

    @Test
    public void testUpdateAndDeleteMatch() {
        Match match = new Match();
        match.setTeam_a("Team X");
        match.setTeam_b("Team Y");
        match.setSport(Sport.FOOTBALL);
        match.setMatchDate(LocalDate.of(2025, 12, 1));

        Match created = restTemplate.postForEntity(url(MATCHES + CREATE_MATCH), match, Match.class).getBody();

        created.setTeam_a("Updated Team X");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Match> entity = new HttpEntity<>(created, headers);
        ResponseEntity<Match> updateResp = restTemplate.exchange(
                url(MATCHES + UPDATE_MATCH.replace("{id}", String.valueOf(created.getId()))) + "?clearOdds=false",
                HttpMethod.PUT,
                entity,
                Match.class);
        assertThat(updateResp.getBody().getTeam_a()).isEqualTo("Updated Team X");

        restTemplate.delete(url(MATCHES + DELETE_MATCH.replace("{id}", String.valueOf(created.getId()))));

        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(url(MATCHES + GET_MATCH.replace("{id}", String.valueOf(created.getId()))), String.class);
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // due to lack of NotFound handling
    }

    @Test
    public void testCreateAndGetOdds() {
        Match match = new Match();
        match.setTeam_a("Odds A");
        match.setTeam_b("Odds B");
        match.setSport(Sport.BASKETBALL);
        match.setMatchDate(LocalDate.of(2025, 12, 1));
        Match createdMatch = restTemplate.postForEntity(url(MATCHES + CREATE_MATCH), match, Match.class).getBody();

        MatchOdds odds = new MatchOdds();
        odds.setSpecifier("1");
        odds.setOdd(BigDecimal.valueOf(1.75));

        ResponseEntity<MatchOdds> oddsResp = restTemplate.postForEntity(
                url(ODDS + CREATE_ODD + "?matchId=" + createdMatch.getId()), odds, MatchOdds.class
        );
        assertThat(oddsResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long oddsId = oddsResp.getBody().getId();

        ResponseEntity<MatchOdds> getOddsResp = restTemplate.getForEntity(url(ODDS + GET_ODD.replace("{id}", String.valueOf(oddsId))), MatchOdds.class);
        assertThat(getOddsResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getOddsResp.getBody().getSpecifier()).isEqualTo("1");
    }
}