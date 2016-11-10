package com.tyrell.replicant.portfolio.service.model;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component("aapl")
public class StubTrade2 implements ITrade {

    @Override
    public BigInteger getNumOfSharesToBuy() {
        return new BigInteger("2");
    }

    @Override
    public String getTicker() {
        return "AAPL";
    }

    @Override
    public BigDecimal getBuyAdvice() {
        return new BigDecimal("1.68");
    }

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal("1.34");
    }

    @Override
    public String getDateTime() {
        return new LocalDateTime().now().plusYears(100).toString();
    }

    @Override
    public BigDecimal getMaxMonthlyInvestment() {
        return new BigDecimal("100.00");
    }

}
