package com.udacity.pricing;


import com.udacity.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PriceTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPrice() throws Exception {
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=1", Price.class);
        System.out.println(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getCurrency(), "USD");
    }

    @Test
    public void getPrices() throws Exception {
        ResponseEntity<String> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/prices", String.class);
        System.out.println(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(response.getBody());
    }

    @Test
    public void addPrice() throws Exception {
        ResponseEntity<String> response =
                this.restTemplate.postForEntity("http://localhost:" + port + "/prices",new Price("USD", new BigDecimal("125.0")), String.class);
        System.out.println(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("USD"));
    }


}
