package com.tyrell.replicant.portfolio.service.model;

import com.tyrell.replicant.portfolio.service.model.Portfolio.PortfolioBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioTest {

    private static final int EXPECTED_SCALE_OF_MONETARY_VALUES = 2;
    @Autowired
    private ITrade intc;
    @Autowired
    private ITrade aapl;

    @Test
    public void testGetNumOfSharesHeld() {
        // given
        BigInteger expected = new BigInteger("10");
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);
        builder.numOfSharesHeld(expected);
        Portfolio underTest = builder.build();

        // when
        BigInteger actual = underTest.getNumOfSharesHeld();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSharesHeldValue() {
        // given
        BigDecimal expected = new BigDecimal(10.00).setScale(EXPECTED_SCALE_OF_MONETARY_VALUES);
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);
        builder.sharesHeldValue(expected);
        Portfolio underTest = builder.build();

        // when
        BigDecimal actual = underTest.getSharesHeldValue();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCashHeldValue() {
        // given
        BigDecimal expected = new BigDecimal(10.00).setScale(EXPECTED_SCALE_OF_MONETARY_VALUES);
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);
        builder.cashHeldValue(expected);
        Portfolio underTest = builder.build();

        // when
        BigDecimal actual = underTest.getCashHeldValue();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAccountValue() {
        // given
        BigDecimal expected = new BigDecimal(10.00).setScale(EXPECTED_SCALE_OF_MONETARY_VALUES);
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);
        builder.accountValue(expected);
        Portfolio underTest = builder.build();

        // when
        BigDecimal actual = underTest.getAccountValue();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPrice() {
        // given
        BigDecimal expected = intc.getPrice();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.build();
        BigDecimal actual = underTest.getPrice();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMaxMonthlyInvestment() {
        // given
        BigDecimal expected = intc.getMaxMonthlyInvestment();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.maxMonthlyInvestment(expected).build();
        BigDecimal actual = underTest.getMaxMonthlyInvestment();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDateTime() {
        // given
        String expected = intc.getDateTime();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.build();
        String actual = underTest.getDateTime();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumOfSharesToBuy() {
        // given
        BigInteger expected = intc.getNumOfSharesToBuy();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.build();
        BigInteger actual = underTest.getNumOfSharesToBuy();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBuyAdvice() {
        // given
        BigDecimal expected = intc.getBuyAdvice();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.build();
        BigDecimal actual = underTest.getBuyAdvice();

        // then
        assertEquals(expected, actual);
    }


    @Test
    public void testGetTicker() {
        // given
        String expected = intc.getTicker();
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(intc);

        // when
        Portfolio underTest = builder.build();
        String actual = underTest.getTicker();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals_ShouldBeTrue() {
        // given
        Portfolio.PortfolioBuilder intelBuilder = new PortfolioBuilder(intc);
        Portfolio p1 = intelBuilder.build();
        Portfolio p2 = intelBuilder.build();

        // when
        boolean shouldBeTrue = p1.equals(p2);

        // then
        assertNotNull(intc);
        assertTrue(shouldBeTrue);
    }

    @Test
    public void testEquals_ShouldBeFalse() {
        // given
        Portfolio.PortfolioBuilder intelBuilder = new PortfolioBuilder(intc);
        Portfolio.PortfolioBuilder appleBuilder = new PortfolioBuilder(aapl);
        Portfolio p1 = intelBuilder.build();
        Portfolio p2 = appleBuilder.build();

        // when
        boolean shouldBeFalse = p1.equals(p2);

        // then
        assertNotNull(intc);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void testHashcode_WhenObjectsAreTheSame() {
        // given
        Portfolio.PortfolioBuilder intelBuilder = new PortfolioBuilder(intc);
        Portfolio p1 = intelBuilder.build();
        Portfolio p2 = intelBuilder.build();

        // when
        int hashcode1 = p1.hashCode();
        int hashcode2 = p2.hashCode();

        // then
        assertTrue(hashcode1 == hashcode2);
    }

    @Test
    public void testHashcode_WhenObjectsAreDifferent() {
        // given
        Portfolio.PortfolioBuilder intelBuilder = new PortfolioBuilder(intc);
        Portfolio.PortfolioBuilder appleBuilder = new PortfolioBuilder(aapl);
        Portfolio p1 = intelBuilder.build();
        Portfolio p2 = appleBuilder.build();

        // when
        int hashcode1 = p1.hashCode();
        int hashcode2 = p2.hashCode();

        // then
        assertFalse(hashcode1 == hashcode2);
    }

}