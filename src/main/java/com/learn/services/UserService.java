package com.learn.services;

import java.util.UUID;

public interface UserService {
    public void signUp(String login, String password);
    public UUID signIn(String login, String password);
    public void signOut(UUID uuid);
}
