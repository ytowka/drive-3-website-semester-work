package com.danilkha.service;

import org.danilkha.dto.UserDto;
import org.danilkha.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final String picturesBasePath;

    public UserServiceImpl(
            String picturesBasePath
    ) {
        this.picturesBasePath = picturesBasePath;
    }

    @Override
    public String getUserAvatarPath(String filename) {
        return picturesBasePath+"/"+filename;
    }

    @Override
    public UserDto getUserById(UUID id) {
        return null;
    }

    @Override
    public void subscribeUser(UUID from, UUID to) {

    }

    @Override
    public List<UserDto> getSubscribers(UUID id) {
        return null;
    }

    @Override
    public List<UserDto> getSubscriptions(UUID id) {
        return null;
    }

}
