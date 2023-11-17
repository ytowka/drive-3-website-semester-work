package com.danilkha.service;

import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.services.UserService;

import java.sql.SQLException;
import java.util.List;
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

    @Override
    public List<UserDto> searchUser(String query) {
        try {
            return userDao.search(query)
                    .stream()
                    .map(userEntity -> userEntity.toDto(picturesBasePath))
                    .toList();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
