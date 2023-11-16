package org.danilkha.services;

import org.danilkha.dto.UserDto;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface SubscriptionsService {

    void subscribeUser(UUID from, UUID to) ;
    void unsubscribeUser(UUID from, UUID to);
    List<UserDto> getSubscribers(UUID userId);
    List<UserDto> getSubscriptions(UUID userId);
}
