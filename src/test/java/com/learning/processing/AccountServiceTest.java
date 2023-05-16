package com.learning.processing;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.models.Account;
import com.learn.services.AccountService;
import com.learn.services.AccountServiceImpl;
import com.learn.services.UserService;
import com.learn.services.UserServiceImpl;
import com.learn.utils.TokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AccountServiceTest {
    private static UserService userService;

    private static AccountService accountService;

    private static InMemoryUserBase inMemoryUserBase;

    private static InMemoryTokenBase inMemoryTokenBase;

    private static UUID validToken;

    @BeforeAll
    static void initUserService() {
        inMemoryUserBase = new InMemoryUserBase();
        inMemoryTokenBase = new InMemoryTokenBase();
        userService = new UserServiceImpl(inMemoryUserBase, inMemoryTokenBase);
        accountService = new AccountServiceImpl(inMemoryUserBase, inMemoryTokenBase);
        userService.signUp("Дементный дед", "цукен");
        validToken = userService.signIn("Дементный дед", "цукен");
    }

    @Test
    void createdAccountIsBelongToUser(){
        //проверяет, что при создании счёта он действительно принадлежит нужному (конкретному) пользователю
        Account account = accountService.openAccount(validToken, 2000);

        Assertions.assertEquals(
                TokenUtils.getUserByToken(validToken, inMemoryUserBase, inMemoryTokenBase).getAccounts().get(0),
                account);
    }

    @Test
    void nonAuthorizedUserHasNoAccessToAccount(){
        //проверяет, что неавторизованный пользователь или другой юзер не может получить доступ к чужому счёту
        Account account = accountService.openAccount(validToken, 2000);

        UUID invalidToken = UUID.randomUUID();

        int accountId = account.getAccountId();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                accountService.closeAccount(invalidToken, accountId));
    }

    @Test
    void accountIsBelongToUser(){
        //проверка, что поле userId класса Account не пустое (то есть создаваемому счёт принадлежит какому-либо пользователю)
        Account account = accountService.openAccount(validToken, 2000);

        Integer userId = account.getUserId();
        Assertions.assertNotNull(userId);
    }

}
