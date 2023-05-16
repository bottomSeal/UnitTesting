package com.learn.database;

import com.learn.models.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserBase {
    private HashMap<Integer, User> users;

    public InMemoryUserBase() {
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public Map<Integer, User> getUsers() {
        return users;
    }
}
