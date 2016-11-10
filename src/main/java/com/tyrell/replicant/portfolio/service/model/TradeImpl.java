package com.tyrell.replicant.portfolio.service.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class TradeImpl implements ITrade {

    private String ticker;
    private String dateTime;
    private BigInteger numOfSharesToBuy;
    private BigDecimal price;
    private BigDecimal buyAdvice;
    private BigDecimal maxMonthlyInvestment;

    private TradeImpl() {
    }

    private TradeImpl(TradeBuilder builder) throws InterruptedException {
        this.price = builder.price;
        this.numOfSharesToBuy = builder.numOfSharesToBuy;
        this.buyAdvice = builder.buyAdvice;
        this.ticker = builder.ticker;
        this.dateTime = builder.dateTime();
        this.maxMonthlyInvestment = builder.maxMonthlyInvestment;
    }

    @Override
    public BigDecimal getMaxMonthlyInvestment() {
        return maxMonthlyInvestment;
    }

    @Override
    public BigInteger getNumOfSharesToBuy() {
        return numOfSharesToBuy;
    }

    @Override
    public String getTicker() {
        return ticker;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public BigDecimal getBuyAdvice() {
        return buyAdvice;
    }

    @Override
    public String getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeImpl trade = (TradeImpl) o;
        return dateTime != null ? dateTime.equals(trade.dateTime) : trade.dateTime == null;

    }

    @Override
    public int hashCode() {
        return dateTime != null ? dateTime.hashCode() : 0;
    }

    public static class TradeBuilder {
        public BigDecimal maxMonthlyInvestment;
        private BigDecimal price;
        private BigInteger numOfSharesToBuy;
        private BigDecimal buyAdvice;
        private String ticker;

        public TradeBuilder maxMonthlyInvestment(BigDecimal maxMonthlyInvestment) {
            this.maxMonthlyInvestment = maxMonthlyInvestment;
            return this;
        }

        public TradeBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public TradeBuilder numOfSharesToBuy(BigInteger numOfSharesToBuy) {
            this.numOfSharesToBuy = numOfSharesToBuy;
            return this;
        }

        public TradeBuilder buyAdvice(BigDecimal buyAdvice) {
            this.buyAdvice = buyAdvice;
            return this;
        }

        public TradeBuilder ticker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public synchronized String dateTime() throws InterruptedException {
            Thread.sleep(500);
            return new DateTime().toLocalDateTime().now().toString();
        }

        public TradeImpl build() throws InterruptedException {
            TradeImpl trade = new TradeImpl(this);
            return trade;
        }

    }
}