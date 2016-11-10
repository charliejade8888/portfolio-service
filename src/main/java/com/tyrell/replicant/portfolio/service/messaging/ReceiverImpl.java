package com.tyrell.replicant.portfolio.service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyrell.replicant.portfolio.service.model.ITrade;
import com.tyrell.replicant.portfolio.service.model.TradeImpl;
import org.json.JSONException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.Thread.sleep;

@Component("receiver")
public class ReceiverImpl implements IReceiver {

    public static final Integer WAIT_FOR_TRADE_TIMEOUT = 5;

    private ITrade tradeImpl;

    @Override
    @JmsListener(destination = "myQueue", containerFactory = "myFactory")
    public void receiveMessage(String tradeString) throws JSONException, IOException {
        tradeImpl = new ObjectMapper().readValue(tradeString, TradeImpl.class);
    }

    @Override
    public ITrade getTrade(int timeoutSeconds) throws InterruptedException {
        waitForTradeToAppearOnQueue(timeoutSeconds);
        return this.tradeImpl;
    }

    private void waitForTradeToAppearOnQueue(long timeout) throws InterruptedException {
        while (this.tradeImpl == null && timeout != 0) {
            sleep(1000);
            timeout--;
        }
        if (this.tradeImpl == null) {
            throw new TradeUnavailableException("Timed out waiting for trade.");
        }
    }

}