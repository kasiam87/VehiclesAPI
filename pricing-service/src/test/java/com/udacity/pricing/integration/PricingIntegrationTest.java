package com.udacity.pricing.integration;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.model.PricesObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canSuccessfullyCallPricesService() {
        ResponseEntity<PricesObject> response =
                  this.restTemplate.getForEntity("http://localhost:" + port + "/prices/", PricesObject.class);

          assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void pricingServiceReturnsNonEmptyListOfPrices() {
        ResponseEntity<PricesObject> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/prices/", PricesObject.class);

        Assert.assertNotNull(response.getBody());
        Assert.assertFalse(response.getBody().embedded.prices.isEmpty());
    }

    @Test
    public void canRetrievePriceByVehicleId() {
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1", Price.class);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getCurrency());
        Assert.assertNotNull(response.getBody().getPrice());
    }
}