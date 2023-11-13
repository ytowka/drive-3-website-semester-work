package com.danilkha.service;

import org.danilkha.services.UserService;

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
}
