package com.learn.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int userId;
    private static int lastId = 0;
    private String login;
    private String password;
    private HashMap<Integer, Account> accounts;

    public User(String login, String password){
        this.login = login;
        this.password = password;
        this.userId = lastId++;
        accounts = new HashMap<>();
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public void deleteAccount(int accountId) {
        accounts.remove(accountId);
    }
}