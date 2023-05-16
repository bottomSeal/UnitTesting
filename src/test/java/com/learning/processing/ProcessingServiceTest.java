package com.learning.processing;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.exeptions.InsufficientFundsException;
import com.learn.exeptions.NegativeBalanceException;
import com.learn.models.Account;
import com.learn.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ProcessingServiceTest {
    private static UserService userService;

    private static AccountService accountService;

    private static ProcessingService processingService;

    private static InMemoryUserBase inMemoryUserBase;

    private static InMemoryTokenBase inMemoryTokenBase;

    private static UUID user1Token;

    private static UUID user2Token;

    @BeforeAll
    static void initUserService() {
        inMemoryUserBase = new InMemoryUserBase();
        inMemoryTokenBase = new InMemoryTokenBase();

        userService = new UserServiceImpl(inMemoryUserBase, inMemoryTokenBase);
        accountService = new AccountServiceImpl(inMemoryUserBase, inMemoryTokenBase);
        processingService = new ProcessingServiceImpl(inMemoryUserBase, inMemoryTokenBase);

        userService.signUp("Дементный дед", "цукен");
        user1Token = userService.signIn("Дементный дед", "цукен");

        userService.signUp("Дурная бабка", "фывфв");
        user2Token = userService.signIn("Дурная бабка", "фывфв");
    }


    @Test
    void transactionsBetweenAccountsIsValid(){
        Account account1 = accountService.openAccount(user1Token, 2000);

        Account account2 = accountService.openAccount(user2Token, 1000);

        processingService.exchangeMoney(account1, account2, 100, user1Token);

        Assertions.assertTrue((account1.getMoney() == 2000 - 100) &&
                (account2.getMoney() == 1000 + 100));
    }

    @Test
    void transactionsBetweenAccountsOfOneUserIsValid(){
        //проверка работоспособности транзакции между счетами одного пользователя
        Account account3 = accountService.openAccount(user1Token, 2000);

        Account account4 =  accountService.openAccount(user1Token, 2000);

        processingService.exchangeMoney(account3, account4, 2000, user1Token);

        Assertions.assertTrue((account3.getMoney() == 2000 - 2000) &&
                (account4.getMoney() == 2000 + 2000));
    }

    @Test
    void transactionRollbackByError(){
        Account account5 = accountService.openAccount(user1Token, 1000);

        Account account6 = accountService.openAccount(user2Token, 2000);

        int moneyInAccount5 = account5.getMoney();
        int moneyInAccount6 = account6.getMoney();

        try {
            processingService.exchangeMoney(account5, account6, 4000, user1Token);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        } finally {
            Assertions.assertTrue((account5.getMoney() == moneyInAccount5) &&
                    (moneyInAccount6 == account6.getMoney()));
        }
    }

    @Test
    void transactionNotPermitIfNegativeDebt(){
        //проверка, что транзакция не осуществляется при отрицательном балансе счёта - отправителя
        Account account7 = accountService.openAccount(user1Token, -1000);

        Account account8 = accountService.openAccount(user2Token, 2000);

        Assertions.assertThrows(NegativeBalanceException.class, () ->
                processingService.exchangeMoney(account7, account8, 4000, user1Token));
    }

}
