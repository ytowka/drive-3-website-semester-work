package com.danilkha.service;

import org.danilkha.dao.SubscriptionsDao;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.entities.SubscriptionEntity;
import org.danilkha.services.SubscriptionsService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SubscriptionsServiceImpl implements SubscriptionsService {

    private final SubscriptionsDao subscriptionsDao;
    private final UserDao userDao;
    private final String baseAvatarPath;

    public SubscriptionsServiceImpl(SubscriptionsDao subscriptionsDao, UserDao userDao, String baseAvatarPath) {
        this.subscriptionsDao = subscriptionsDao;
        this.userDao = userDao;
        this.baseAvatarPath = baseAvatarPath;
    }

    @Override
    public void subscribeUser(UUID from, UUID to) {
        try {
            subscriptionsDao.subscribeUser(new SubscriptionEntity(from, to));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribeUser(UUID from, UUID to) {
        try {
            subscriptionsDao.unsubscribeUser(new SubscriptionEntity(from, to));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDto> getSubscribers(UUID userId) {
        try {
            return subscriptionsDao.getSubscribers(userId)
                    .stream()
                    .map(userEntity -> userEntity.toDto(baseAvatarPath))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDto> getSubscriptions(UUID userId) {
        try {
            return subscriptionsDao.getSubscriptions(userId)
                    .stream()
                    .map(userEntity -> userEntity.toDto(baseAvatarPath))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
