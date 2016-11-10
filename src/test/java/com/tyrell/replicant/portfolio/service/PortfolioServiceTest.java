package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.messaging.IReceiver;
import com.tyrell.replicant.portfolio.service.model.ITrade;
import com.tyrell.replicant.portfolio.service.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioServiceTest {

    @Autowired
    private IPortfolioService portfolioServiceImpl;

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private ITrade aapl;

    @Autowired
    private IReceiver testReceiverIntc;

    @Autowired
    private IReceiver testReceiverAapl;

    @Before
    public void setUp() {
        mongoTemplate.remove(new Query(), Portfolio.class);
    }

    @Test
    public void testRetrievePortfolio_retrievesZeroWhenZeroPersisted() throws InterruptedException {
        // given
        int expected = 0;

        // when
        List<Portfolio> list = portfolioServiceImpl.retrievePortfolios();

        // then
        int actual = list.size();
        assertEquals(expected, actual);
    }

    @Test
    public void testRetrievePortfolios_retrievesOneWhenOnePersisted() throws InterruptedException {
        // given
        String fieldToReplace = "receiver";
        setField(portfolioServiceImpl, fieldToReplace, testReceiverIntc);
        portfolioServiceImpl.persistPortfolio();
        int expected = 1;

        // when
        List<Portfolio> list = portfolioServiceImpl.retrievePortfolios();

        // then
        int actual = list.size();
        assertEquals(expected, actual);
    }

    @Test
    public void testRetrievePortfolios_retrievesTwoWhenTwoPersisted() throws InterruptedException {
        // given
        long expectedInitialCount = 0;
        long expectedFinalCount = 2;
        List<Portfolio> list = portfolioServiceImpl.retrievePortfolios();
        int actualInitialCount = list.size();
        String fieldToReplace = "receiver";
        setField(portfolioServiceImpl, fieldToReplace, testReceiverIntc);
        portfolioServiceImpl.persistPortfolio();
        setField(portfolioServiceImpl, fieldToReplace, testReceiverAapl);
        portfolioServiceImpl.persistPortfolio();

        // when
        list = portfolioServiceImpl.retrievePortfolios();

        // then
        int actualFinalCount = list.size();
        assertEquals(expectedInitialCount, actualInitialCount);
        assertEquals(expectedFinalCount, actualFinalCount);
    }

    @Test(expected = DuplicateKeyException.class)
    public void testRetrievePortfolios_retrievesOneWhenTwoPersistedFirstHasLaterDateTime() throws InterruptedException {
        // given
        long expectedInitialCount = 0;
        long expectedFinalCount = 1;
        List<Portfolio> list = portfolioServiceImpl.retrievePortfolios();
        int actualInitialCount = list.size();
        String fieldToReplace = "receiver";
        setField(portfolioServiceImpl, fieldToReplace, testReceiverAapl);
        portfolioServiceImpl.persistPortfolio();
        setField(portfolioServiceImpl, fieldToReplace, testReceiverIntc);
        portfolioServiceImpl.persistPortfolio();

        // when
        list = portfolioServiceImpl.retrievePortfolios();

        // then
        int actualFinalCount = list.size();
        assertEquals(expectedInitialCount, actualInitialCount);
        assertEquals(expectedFinalCount, actualFinalCount);
    }
}
