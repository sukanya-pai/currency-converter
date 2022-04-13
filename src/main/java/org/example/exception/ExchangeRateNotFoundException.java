package org.example.exception;

public class ExchangeRateNotFoundException extends Exception{
    public ExchangeRateNotFoundException(String message){
        super(message);
    }
}
