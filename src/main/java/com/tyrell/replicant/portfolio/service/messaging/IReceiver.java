package com.tyrell.replicant.portfolio.service.messaging;

import com.tyrell.replicant.portfolio.service.model.ITrade;
import org.json.JSONException;

import java.io.IOException;

public interface IReceiver {

    void receiveMessage(String trade) throws JSONException, IOException;

    ITrade getTrade(int timeoutSeconds) throws InterruptedException;

}
