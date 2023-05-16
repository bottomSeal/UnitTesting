package com.learn.services;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.models.User;
import com.learn.utils.TokenUtils;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    InMemoryUserBase userBase;
    InMemoryTokenBase tokenBase;

    public UserServiceImpl(InMemoryUserBase userBase, InMemoryTokenBase tokenBase) {
        this.userBase = userBase;
        this.tokenBase = tokenBase;
    }

    @Override
    public void signUp(String login, String password) {
        boolean isUserExists = userBase.getUsers().entrySet().stream()
                .anyMatch(user -> user.getValue().getLogin().equals(login));

        if (isUserExists) {
            throw new IllegalArgumentException("User with same login already exists.");
        }

        User newUser = new User(login, password);
        userBase.addUser(newUser);
    }

    @Override
    public UUID signIn(String login, String password) {
        User existingUser = userBase.getUsers().values().stream()
                .filter(user -> user.getLogin().equals(login)
                        && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (existingUser == null) {
            throw new IllegalArgumentException("Incorrect login or password.");
        }

        UUID accessToken = UUID.randomUUID();
        tokenBase.addToken(existingUser.getUserId(), accessToken);

        return accessToken;
    }

    @Override
    public void signOut(UUID accessToken) {
        User user = TokenUtils.getUserByToken(accessToken, userBase, tokenBase);

        tokenBase.deleteToken(user.getUserId());
    }
}
