package com.danilkha.service;

import org.danilkha.dto.UserDto;
import org.danilkha.services.SubscriptionsService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SubscriptionsServiceImpl implements SubscriptionsService {
    @Override
    public void subscribeUser(UUID from, UUID to) throws SQLException {

    }

    @Override
    public void unsubscribeUser(UUID from, UUID to) throws SQLException {

    }

    @Override
    public List<UserDto> getSubscribers(UUID userId) throws SQLException {
        return null;
    }

    @Override
    public List<UserDto> getSubscriptions(UUID userId) throws SQLException {
        return null;
    }
}
