package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.model.Portfolio;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PortfolioController {

    @Autowired
    private IPortfolioService portfolioServiceImpl;

    @CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Returns portfolio history from Mongo database.")
    @RequestMapping(value = "/portfolio", method = RequestMethod.GET)
    public ResponseEntity<List<Portfolio>> getPortfolio() {
        return new ResponseEntity<>(portfolioServiceImpl.retrievePortfolios(), OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Persists portfolio to Mongo database.", notes = "Portfolio generated using trade received from activemq.")
    @RequestMapping(value = "/portfolio", method = RequestMethod.POST)
    public ResponseEntity<String> postPortfolio() throws InterruptedException {
        portfolioServiceImpl.persistPortfolio();
        return new ResponseEntity<>(CREATED);
    }
}
