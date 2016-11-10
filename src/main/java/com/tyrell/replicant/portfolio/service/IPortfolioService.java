package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.model.Portfolio;

import java.util.List;

public interface IPortfolioService {

    void persistPortfolio() throws InterruptedException;

    List<Portfolio> retrievePortfolios();

}
