package com.learn.utils;

import com.learn.database.InMemoryTokenBase;
import com.learn.database.InMemoryUserBase;
import com.learn.models.User;

import java.util.Map;
import java.util.UUID;

public class TokenUtils {
    private TokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static User getUserByToken(UUID accessToken, InMemoryUserBase userBase, InMemoryTokenBase tokenBase){
        int userId = tokenBase.getTokens().entrySet().stream()
                .filter(token -> token.getValue().equals(accessToken))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);

        if (userId == -1) {
            throw new IllegalArgumentException("Invalid token");
        }

        User user = userBase.getUsers().entrySet().stream()
                .filter(userEntry -> userEntry.getValue().getUserId() == userId)
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new NullPointerException();
        }

        return user;
    }
}
