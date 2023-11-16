package org.danilkha.services;

import org.danilkha.dto.UserDto;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface SubscriptionsService {

    void subscribeUser(UUID from, UUID to) throws SQLException;
    void unsubscribeUser(UUID from, UUID to) throws SQLException;
    List<UserDto> getSubscribers(UUID userId) throws SQLException;
    List<UserDto> getSubscriptions(UUID userId) throws SQLException;
}
