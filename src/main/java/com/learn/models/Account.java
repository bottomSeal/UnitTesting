package com.learn.models;

public class Account {
    private final int accountId;
    private static int lastId = 0;
    private final int userId;
    private int money;

    public Account(User user, int money){
        this.userId = user.getUserId();
        this.money = money;
        this.accountId = lastId++;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoney() {
        return money;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
