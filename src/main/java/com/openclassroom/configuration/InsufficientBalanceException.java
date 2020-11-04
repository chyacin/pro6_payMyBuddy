package com.openclassroom.configuration;

public class InsufficientBalanceException extends Exception{

    public InsufficientBalanceException(String message){
        super(message);
    }

}
