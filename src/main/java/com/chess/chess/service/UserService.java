package com.chess.chess.service;

import com.chess.chess.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}