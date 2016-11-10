package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.model.ITrade;
import com.tyrell.replicant.portfolio.service.model.Portfolio;
import com.tyrell.replicant.portfolio.service.model.Portfolio.PortfolioBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("testPortfolioService")
public class StubPortfolioService implements IPortfolioService {

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private ITrade aapl;

    @Override
    public void persistPortfolio() {
        // nothing to do here!!
    }

    @Override
    public List<Portfolio> retrievePortfolios() {
        List<Portfolio> portfolios;
        portfolios = new ArrayList<>();
        Portfolio.PortfolioBuilder appleBuilder = new Portfolio.PortfolioBuilder(aapl);
        Portfolio portfolio = appleBuilder.build();
        portfolios.add(portfolio);
        return portfolios;
    }

}
