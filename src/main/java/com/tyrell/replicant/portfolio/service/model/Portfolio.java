package com.tyrell.replicant.portfolio.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigDecimal.*;

@Document
public class Portfolio {

    @Id
    private String dateTime;
    private String ticker;
    private BigInteger numOfSharesToBuy;
    private BigInteger numOfSharesHeld;
    private BigDecimal sharesHeldValue;
    private BigDecimal cashHeldValue;
    private BigDecimal accountValue;
    private BigDecimal price;
    private BigDecimal buyAdvice;

    private BigDecimal maxMonthlyInvestment;

    private Portfolio() {
        super();
    }

    private Portfolio(ITrade trade, PortfolioBuilder builder) {
        this.price = trade.getPrice();
        this.buyAdvice = trade.getBuyAdvice();
        this.ticker = trade.getTicker();
        this.numOfSharesToBuy = trade.getNumOfSharesToBuy();
        this.dateTime = trade.getDateTime();
        this.numOfSharesHeld = builder.numOfSharesHeld;
        this.sharesHeldValue = builder.sharesHeldValue;
        this.cashHeldValue = builder.cashHeldValue;
        this.accountValue = builder.accountValue;
        this.maxMonthlyInvestment = builder.maxMonthlyInvestment;
    }

    public BigDecimal getMaxMonthlyInvestment() {
        return maxMonthlyInvestment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getBuyAdvice() {
        return buyAdvice;
    }

    public String getTicker() {
        return ticker;
    }

    public BigInteger getNumOfSharesToBuy() {
        return numOfSharesToBuy;
    }

    public BigInteger getNumOfSharesHeld() {
        return numOfSharesHeld;
    }

    public BigDecimal getSharesHeldValue() {
        return sharesHeldValue;
    }

    public BigDecimal getCashHeldValue() {
        return cashHeldValue;
    }

    public BigDecimal getAccountValue() {
        return accountValue;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (dateTime == null) return false;
        Portfolio portfolio = (Portfolio) o;
        return dateTime.equals(portfolio.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime != null ? dateTime.hashCode() : 0;
    }

    public static class PortfolioBuilder {
        private static final int SCALE_FOR_MONETARY_VALUES = 2;
        private ITrade trade;
        private BigInteger numOfSharesHeld;
        private BigDecimal sharesHeldValue;
        private BigDecimal cashHeldValue;
        private BigDecimal accountValue;
        private BigDecimal maxMonthlyInvestment;

        public PortfolioBuilder(ITrade trade) {
            this.trade = trade;
        }

        public PortfolioBuilder numOfSharesHeld(BigInteger numOfSharesHeld) {
            this.numOfSharesHeld = numOfSharesHeld;
            return this;
        }

        public PortfolioBuilder sharesHeldValue(BigDecimal sharesHeldValue) {
            this.sharesHeldValue = sharesHeldValue.setScale(SCALE_FOR_MONETARY_VALUES, ROUND_DOWN);
            return this;
        }

        public PortfolioBuilder maxMonthlyInvestment(BigDecimal maxMonthlyInvestment) {
            this.maxMonthlyInvestment = maxMonthlyInvestment.setScale(SCALE_FOR_MONETARY_VALUES, ROUND_DOWN);
            return this;
        }

        public PortfolioBuilder cashHeldValue(BigDecimal cashHeldValue) {
            this.cashHeldValue = cashHeldValue.setScale(SCALE_FOR_MONETARY_VALUES, ROUND_DOWN);
            return this;
        }

        public PortfolioBuilder accountValue(BigDecimal accountValue) {
            this.accountValue = accountValue.setScale(SCALE_FOR_MONETARY_VALUES, ROUND_DOWN);
            return this;
        }

        public Portfolio build() {
            Portfolio portfolio = new Portfolio(trade, this);
            return portfolio;
        }
    }
}