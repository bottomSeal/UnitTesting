package com.learning.processing;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.services.AccountService;
import com.learn.services.AccountServiceImpl;
import com.learn.services.UserService;
import com.learn.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

class UserServiceTest {
    private static UserService userService;

    private static AccountService accountService;

    private static InMemoryUserBase inMemoryUserBase;

    private static InMemoryTokenBase inMemoryTokenBase;

    private static UUID accessToken;

    @BeforeAll
    static void initUserService() {
        inMemoryUserBase = new InMemoryUserBase();
        inMemoryTokenBase = new InMemoryTokenBase();
        userService = new UserServiceImpl(inMemoryUserBase, inMemoryTokenBase);
        accountService = new AccountServiceImpl(inMemoryUserBase, inMemoryTokenBase);
    }

    @Test
    void isUserRegisterCorrectly(){
        //Проверка, что пользователь регистрируется в in-memory базу
        userService.signUp("Дементный дед", "qwerty");

        Assertions.assertTrue(inMemoryUserBase.getUsers().values()
                .stream()
                .anyMatch(user ->
                        user.getLogin().equals("Дементный дед") &&
                                user.getPassword().equals("qwerty")));
    }

    @Test
    void isUserAuthCorrectly(){
        //Проверка, что пользователь успешно авторизовался
        accessToken = userService.signIn("Дементный дед", "qwerty");

        Assertions.assertTrue(inMemoryTokenBase.getTokens().values()
                .stream()
                .anyMatch(token -> token.equals(accessToken)));
    }

    @Test
    void userIsAbleToCreateAccount(){
        //Проверка, что пользователь может создавать счёт
        accountService.openAccount(accessToken, 100);

        int userId = inMemoryTokenBase.getTokens().entrySet().stream()
                .filter(token -> token.getValue().equals(accessToken))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);

        Assertions.assertFalse(inMemoryUserBase.getUsers().entrySet()
                .stream()
                .filter(user -> user.getValue().getUserId() == userId)
                .findAny().get().getValue().getAccounts().isEmpty());
    }

    @Test
    void isUserSignOutCorrectly(){
        //Проверка, что пользователь успешно вышел из аккаунта
        userService.signOut(accessToken);

        Assertions.assertTrue(inMemoryTokenBase.getTokens().values()
                .stream()
                .noneMatch(token -> token.equals(accessToken)));
    }
}
