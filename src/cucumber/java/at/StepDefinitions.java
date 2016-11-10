package at;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.tyrell.replicant.portfolio.service.Application;
import com.tyrell.replicant.portfolio.service.model.Portfolio;
import com.tyrell.replicant.portfolio.service.model.TradeImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private JmsOperations jmsTemplate;

    private static final String REQUEST_PATH = "/portfolio";
    private static Response lastResponse;

    @Given("^the database is empty$")
    public void the_database_is_empty() throws Throwable {
        mongoTemplate.remove(new Query(), Portfolio.class);
    }

    @Given("^The following trades are executed:$")
    public void the_following_trades_are_executed(List<TradeImpl> trades) throws Throwable {
        int expectedStatusCode = HttpStatus.CREATED.value();
        String queueThatPortfolioServiceListensTo = "myQueue";
        for (TradeImpl trade : trades) {
            String tradeTextMessage = new Gson().toJson(trade);
            jmsTemplate.convertAndSend(queueThatPortfolioServiceListensTo, tradeTextMessage);
            lastResponse = RestAssured.given().port(port).when().post(REQUEST_PATH);
            int actualStatusCode = lastResponse.getStatusCode();
            assertEquals(expectedStatusCode, actualStatusCode);
        }
    }

    @When("^a request is made to retrieve all portfolios$")
    public void a_request_is_made_to_retrieve_all_portfolios() throws Throwable {
        lastResponse = RestAssured.given().port(port).when().get(REQUEST_PATH);
    }

    @Then("^the following portfolios should be returned:$")
    public void the_following_portfolios_should_be_returned(List<Portfolio> expectedList) throws Throwable {
        List<Portfolio> actualList = Arrays.asList(lastResponse.getBody().as(Portfolio[].class));
        for (int index = 0; index < expectedList.size(); index++) {
            Portfolio actual = actualList.get(index);
            Portfolio expected = expectedList.get(index);
            assertPortfolioMatchExpectations(expected, actual);
        }
    }

    private void assertScaleAndValueAreEqual(BigDecimal expected, BigDecimal actual) {
        int expectedScale = 2;
        assertEquals(expected.setScale(expectedScale), actual);
    }

    private void assertPortfolioMatchExpectations(Portfolio expected, Portfolio actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getDateTime(), actual.getDateTime());
        assertEquals(expected.getTicker(), actual.getTicker());
        assertEquals(expected.getNumOfSharesToBuy(), actual.getNumOfSharesToBuy());
        assertEquals(expected.getNumOfSharesHeld(), actual.getNumOfSharesHeld());
        assertScaleAndValueAreEqual(expected.getPrice(), actual.getPrice());
        assertScaleAndValueAreEqual(expected.getBuyAdvice(), actual.getBuyAdvice());
        assertScaleAndValueAreEqual(expected.getCashHeldValue(), actual.getCashHeldValue());
        assertScaleAndValueAreEqual(expected.getAccountValue(), actual.getAccountValue());
        assertScaleAndValueAreEqual(expected.getMaxMonthlyInvestment(), actual.getMaxMonthlyInvestment());
    }
}