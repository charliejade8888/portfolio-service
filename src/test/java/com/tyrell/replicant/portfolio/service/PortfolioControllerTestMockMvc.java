package com.tyrell.replicant.portfolio.service;

import com.tyrell.replicant.portfolio.service.model.ITrade;
import com.tyrell.replicant.portfolio.service.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioControllerTestMockMvc {

    @Mock
    private static IPortfolioService mockService;

    @InjectMocks
    private static PortfolioController underTest;
    private static List<Portfolio> portfolios;

    @Autowired
    private ITrade aapl;

    @Before
    public void makeAFreshPortfolioListForEachTestCase() {
        portfolios = new ArrayList<>();
        Portfolio.PortfolioBuilder appleBuilder = new Portfolio.PortfolioBuilder(aapl);
        Portfolio portfolio = appleBuilder.build();
        portfolios.add(portfolio);
    }

    @Test
    public void testGetPortfolios() throws Exception {
        // given
        String tickerJsonPath = "[0]ticker";
        String ticker = "AAPL";
        String urlPath = "/portfolio";
        MockMvc mockMvc = standaloneSetup(underTest).build();
        when(mockService.retrievePortfolios()).thenReturn(portfolios);

        // when
        mockMvc.perform(get(urlPath)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath(tickerJsonPath).value(ticker));

        // then
        // nothing to do here!!
    }

    @Test
    public void testPostPortfolio_whenStartingPortfolio() throws Exception {
        // given
        MockMvc mockMvc = standaloneSetup(underTest).build();
        String urlPath = "/portfolio";

        // when
        mockMvc.perform(post(urlPath)).andDo(print()).andExpect(status().isCreated());

        // then
        // nothing to do here!!
    }

}
