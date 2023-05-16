package com.learn.database;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryTokenBase {
    private HashMap<Integer, UUID> tokens;

    public InMemoryTokenBase() {
        this.tokens = new HashMap<>();
    }

    public void addToken(int userId, UUID uuid) {
        tokens.put(userId, uuid);
    }

    public Map<Integer, UUID> getTokens() {
        return tokens;
    }

    public void deleteToken(int userId) {
        tokens.remove(userId);
    }
}
