package com.danilkha.service;

import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.services.UserService;

import java.sql.SQLException;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final String picturesBasePath;
    private final UserDao userDao;

    public UserServiceImpl(
            String picturesBasePath,
            UserDao userDao) {
        this.picturesBasePath = picturesBasePath;
        this.userDao = userDao;
    }

    @Override
    public UserDto getUserById(UUID id) {
        try {
            return userDao.getById(id).toDto(picturesBasePath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
