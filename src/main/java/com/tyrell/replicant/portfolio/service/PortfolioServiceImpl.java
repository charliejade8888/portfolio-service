package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.messaging.IReceiver;
import com.tyrell.replicant.portfolio.service.model.ITrade;
import com.tyrell.replicant.portfolio.service.model.Portfolio;
import com.tyrell.replicant.portfolio.service.model.Portfolio.PortfolioBuilder;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.tyrell.replicant.portfolio.service.messaging.ReceiverImpl.WAIT_FOR_TRADE_TIMEOUT;


@Component
public class PortfolioServiceImpl implements IPortfolioService {

    @Autowired
    private IReceiver receiver;

    @Autowired
    private MongoOperations mongoTemplate;

    @Override
    public void persistPortfolio() throws InterruptedException {
        Portfolio portfolio = null;
        ITrade trade = receiver.getTrade(WAIT_FOR_TRADE_TIMEOUT);
        if (mongoTemplate.count(new Query(), Portfolio.class) == 0) {
            portfolio = startPortfolio(trade);
            mongoTemplate.save(portfolio);
        } else {
            portfolio = updatePortfolio(trade);
            mongoTemplate.insert(portfolio);
        }
    }

    @Override
    public List<Portfolio> retrievePortfolios() {
        return mongoTemplate.findAll(Portfolio.class);
    }

    private Portfolio startPortfolio(ITrade t) {
        Portfolio.PortfolioBuilder builder = new PortfolioBuilder(t);
        Portfolio p = builder.numOfSharesHeld(t.getNumOfSharesToBuy())
                .sharesHeldValue(t.getBuyAdvice())
                .cashHeldValue(t.getMaxMonthlyInvestment().subtract(t.getBuyAdvice()))
                .accountValue(t.getMaxMonthlyInvestment())
                .maxMonthlyInvestment(t.getMaxMonthlyInvestment())
                .build();
        return p;
    }

    private Portfolio updatePortfolio(ITrade t) {
        int lastIndex = (int) mongoTemplate.count(new Query(), Portfolio.class) - 1;
        Portfolio last = retrievePortfolios().get(lastIndex);
        Portfolio updated;
        if (tradeIsNewerThanLastTrade(t.getDateTime())) {
            BigInteger numOfSharesHeldUpdated = last.getNumOfSharesHeld().add(t.getNumOfSharesToBuy());
            BigDecimal cashHeldValueUpdated = last.getMaxMonthlyInvestment().subtract(last.getBuyAdvice()).add(last.getCashHeldValue());
            BigDecimal sharesHeldValueUpdated = last.getSharesHeldValue().add(t.getBuyAdvice());
            BigDecimal accountValueUpdated = sharesHeldValueUpdated.add(cashHeldValueUpdated);
            Portfolio.PortfolioBuilder builder = new PortfolioBuilder(t);
            updated = builder.numOfSharesHeld(numOfSharesHeldUpdated)
                    .sharesHeldValue(sharesHeldValueUpdated)
                    .cashHeldValue(cashHeldValueUpdated)
                    .accountValue(accountValueUpdated)
                    .maxMonthlyInvestment(last.getMaxMonthlyInvestment())
                    .build();
            return updated;
        }
        return last;
    }

    private boolean tradeIsNewerThanLastTrade(String trade) {
        int latest = (int) mongoTemplate.count(new Query(), Portfolio.class) - 1;
        Portfolio p = retrievePortfolios().get(latest);
        String lastTrade = p.getDateTime();
        LocalDateTime tradeDateTime = new LocalDateTime(trade);
        LocalDateTime lastTradeDateTime = new LocalDateTime(lastTrade);
        boolean newerThan = tradeDateTime.isAfter(lastTradeDateTime);
        return newerThan && !lastTrade.equals(trade);
    }

}