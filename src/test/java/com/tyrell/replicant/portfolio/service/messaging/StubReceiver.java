package com.tyrell.replicant.portfolio.service.messaging;

import com.tyrell.replicant.portfolio.service.model.ITrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testReceiverIntc")
public class StubReceiver implements IReceiver {

    @Autowired
    private ITrade intc;

    @Override
    public void receiveMessage(String trade) {
        System.out.println("Received <" + trade + ">");
    }

    @Override
    public ITrade getTrade(int timeoutSeconds) {
        return intc;
    }

}
