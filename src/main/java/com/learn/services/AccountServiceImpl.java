package com.learn.services;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.models.Account;
import com.learn.models.User;
import com.learn.utils.TokenUtils;

import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    InMemoryUserBase userBase;
    InMemoryTokenBase tokenBase;

    public AccountServiceImpl(InMemoryUserBase userBase, InMemoryTokenBase tokenBase) {
        this.userBase = userBase;
        this.tokenBase = tokenBase;
    }

    @Override
    public Account openAccount(UUID accessToken, int money) {
        User user = TokenUtils.getUserByToken(accessToken, userBase, tokenBase);

        Account newAccount = new Account(user, money);

        user.addAccount(newAccount);

        return newAccount;
    }

    @Override
    public void closeAccount(UUID accessToken, int accountId) {
        User user = TokenUtils.getUserByToken(accessToken, userBase, tokenBase);

        user.deleteAccount(accountId);
    }
}
