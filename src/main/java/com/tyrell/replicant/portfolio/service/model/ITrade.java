package com.tyrell.replicant.portfolio.service.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ITrade {

    BigInteger getNumOfSharesToBuy();

    String getTicker();

    BigDecimal getPrice();

    BigDecimal getBuyAdvice();

    String getDateTime();

    BigDecimal getMaxMonthlyInvestment();

}
