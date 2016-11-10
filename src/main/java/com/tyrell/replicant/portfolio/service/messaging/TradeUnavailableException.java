package com.tyrell.replicant.portfolio.service.messaging;

public class TradeUnavailableException extends RuntimeException {

    public TradeUnavailableException(String message) {
        super(message);
    }
}
