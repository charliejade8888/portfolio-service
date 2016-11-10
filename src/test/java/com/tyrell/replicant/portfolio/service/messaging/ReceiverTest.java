package com.tyrell.replicant.portfolio.service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyrell.replicant.portfolio.service.model.ITrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static com.tyrell.replicant.portfolio.service.messaging.ReceiverImpl.WAIT_FOR_TRADE_TIMEOUT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiverTest {

    @Autowired
    private JmsOperations jmsTemplate;

    @Autowired
    private IReceiver receiver;

    @Autowired
    private ITrade intc;

    @Test
    public void testReceiveMessage() throws Exception {
        //given
        ITrade expected = intc;
        String intcString = new ObjectMapper().writeValueAsString(intc);
        jmsTemplate.convertAndSend("myQueue", intcString);

        //when
        ITrade actual = receiver.getTrade(WAIT_FOR_TRADE_TIMEOUT + 20);

        //then
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }

    @Test(expected = TradeUnavailableException.class)
    public void testReceiveMessageThrowsTradeUnavailableException() throws Exception {
        //given
        int doNotWaitForTrade = 0;
        ObjectMapper mapper = new ObjectMapper();
        String intcString = mapper.writeValueAsString(intc);
        jmsTemplate.convertAndSend("myQueue", intcString);

        //when
        receiver.getTrade(doNotWaitForTrade);

        //then
        // nothing to do here!!
    }

}