package com.learn.services;

import com.learn.models.Account;

import java.util.UUID;

public interface AccountService {
    public Account openAccount(UUID accessToken, int money);
    public void closeAccount(UUID accessToken, int accountId);
}
