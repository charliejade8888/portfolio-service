package com.tyrell.replicant.portfolio.service;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortfolioControllerTestRestAssured {

    @LocalServerPort
    private int port;

    @Autowired
    IPortfolioService testPortfolioService;

    @Autowired
    PortfolioController controller;

    private static final String REQUEST_PATH = "/portfolio";

    @Before
    public void setUp() {
        String fieldToReplace = "portfolioServiceImpl";
        setField(controller, fieldToReplace, testPortfolioService);
    }

    @Test
    public void testGetPortfolios() {
        // given
        int expectedStatusCode = 200;

        // when
        Response response = RestAssured.given().port(port).when().get(REQUEST_PATH);
        System.err.println(response.getBody().asString());

        // then
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Test
    public void testPostPortfolio() {
        // given
        int expectedStatusCode = 201;

        // when
        Response response = RestAssured.given().port(port).when().post(REQUEST_PATH);

        // then
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

}