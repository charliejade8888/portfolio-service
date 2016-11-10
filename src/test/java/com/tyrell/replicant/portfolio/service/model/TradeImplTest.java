package com.tyrell.replicant.portfolio.service.model;

import com.tyrell.replicant.portfolio.service.model.TradeImpl.TradeBuilder;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.reflect.Modifier.isSynchronized;
import static org.junit.Assert.*;

public class TradeImplTest {

    @Test
    public void testGetPrice() throws InterruptedException {
        // given
        BigDecimal expected = new BigDecimal(10.25);
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        builder.price(expected);

        // whenz
        TradeImpl underTest = builder.build();
        BigDecimal actual = underTest.getPrice();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDateTime() throws InterruptedException {
        // given
        TradeImpl.TradeBuilder builder = new TradeBuilder();

        // when
        TradeImpl underTest = builder.build();
        String actual = underTest.getDateTime();

        // then
        assertNotNull(actual);
    }

    @Test
    public void testThreadsCanOnlyUseTradeBuilderInstanceForDateTimeMethodSynchronously() throws InterruptedException, NoSuchMethodException {
        //given
        String dateTimeMethodName = "dateTime";
        Class tradeBuilderClass = TradeBuilder.class;
        Method dateTimeMethod = tradeBuilderClass.getMethod(dateTimeMethodName);

        //when
        boolean shouldBeSynchronized = isSynchronized(dateTimeMethod.getModifiers());

        //then
        assertTrue(shouldBeSynchronized);
    }

    @Test
    public void testGetNumOfSharesToBuy() throws InterruptedException {
        // given
        BigInteger expected = new BigInteger("58");
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        builder.numOfSharesToBuy(expected);

        // when
        TradeImpl underTest = builder.build();
        BigInteger actual = underTest.getNumOfSharesToBuy();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBuyAdvice() throws InterruptedException {
        // given
        BigDecimal expected = new BigDecimal(500.25);
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        builder.buyAdvice(expected);

        // when
        TradeImpl underTest = builder.build();
        BigDecimal actual = underTest.getBuyAdvice();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMaxMonthlyInvestment() throws InterruptedException {
        // given
        BigDecimal expected = new BigDecimal(500.25);
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        builder.maxMonthlyInvestment(expected);

        // when
        TradeImpl underTest = builder.build();
        BigDecimal actual = underTest.getMaxMonthlyInvestment();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTicker() throws InterruptedException {
        // given
        String expected = "INTC";
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        builder.ticker(expected);

        // when
        TradeImpl underTest = builder.build();
        String actual = underTest.getTicker();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals_ShouldPass() throws InterruptedException {
        // given
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        TradeImpl trade1 = builder.build();
        TradeImpl trade2 = trade1;

        // when
        boolean shouldBeTrue = trade1.equals(trade2);

        // then
        assertTrue(shouldBeTrue);
    }

    @Test
    public void testEquals_ShouldFail() throws InterruptedException {
        // given
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        TradeImpl trade1 = builder.build();
        TradeImpl trade2 = builder.build();

        // when
        boolean shouldBeFalse = trade1.equals(trade2);

        // then
        assertFalse(shouldBeFalse);
    }

    @Test
    public void testHashcode_WhenObjectsAreTheSame() throws InterruptedException {
        // given
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        TradeImpl trade1 = builder.build();
        TradeImpl trade2 = trade1;

        // when
        int hashcode1 = trade1.hashCode();
        int hashcode2 = trade2.hashCode();

        // then
        assertTrue(hashcode1 == hashcode2);
    }

    @Test
    public void testHashcode_WhenObjectsAreDifferent() throws InterruptedException {
        // given
        TradeImpl.TradeBuilder builder = new TradeBuilder();
        TradeImpl trade1 = builder.build();
        TradeImpl trade2 = builder.build();

        // when
        int hashcode1 = trade1.hashCode();
        int hashcode2 = trade2.hashCode();

        // then
        assertFalse(hashcode1 == hashcode2);
    }

}