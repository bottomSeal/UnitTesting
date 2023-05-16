package com.learn.services;

import com.learn.models.Account;

import java.util.UUID;

public interface ProcessingService {
    public void exchangeMoney(Account accountFrom, Account accountTo, int amountToSend, UUID accessToken);
}
