package com.learn.services;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.exeptions.InsufficientFundsException;
import com.learn.exeptions.NegativeBalanceException;
import com.learn.models.Account;
import com.learn.models.User;
import com.learn.utils.TokenUtils;

import java.util.UUID;

public class ProcessingServiceImpl implements ProcessingService {
    InMemoryUserBase userBase;
    InMemoryTokenBase tokenBase;

    public ProcessingServiceImpl(InMemoryUserBase userBase, InMemoryTokenBase tokenBase) {
        this.userBase = userBase;
        this.tokenBase = tokenBase;
    }

    @Override
    public void exchangeMoney(Account accountFrom, Account accountTo, int amountToSend, UUID accessToken){
        User source = TokenUtils.getUserByToken(accessToken, userBase, tokenBase);

        boolean isSourceWantToSend = source.getAccounts().entrySet()
                .stream()
                .anyMatch(account -> account.getValue().equals(accountFrom));

        if (!isSourceWantToSend) {
            throw new IllegalArgumentException("Invalid operation.");
        }

        if (accountFrom.getMoney() < 0) {
            throw new NegativeBalanceException("Negative Balance");
        }

        if (accountFrom.getMoney() < amountToSend) {
            throw new InsufficientFundsException("Insufficient Funds");
        }

        accountFrom.setMoney(accountFrom.getMoney() - amountToSend);
        accountTo.setMoney(accountTo.getMoney() + amountToSend);
    }
}
