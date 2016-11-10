package com.tyrell.replicant.portfolio.service.messaging;

import com.tyrell.replicant.portfolio.service.model.ITrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("testReceiverAapl")
public class StubReceiver2 implements IReceiver {

    @Autowired
    private ITrade aapl;

    @Override
    public void receiveMessage(String trade) {
        System.out.println("Received <" + trade + ">");
    }

    @Override
    public ITrade getTrade(int timeoutSeconds) {
        return aapl;
    }

}
