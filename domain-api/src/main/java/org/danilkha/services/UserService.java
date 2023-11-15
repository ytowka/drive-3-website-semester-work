package org.danilkha.services;

import org.danilkha.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    String getUserAvatarPath(String filename);
    UserDto getUserById(UUID id);
    void subscribeUser(UUID from, UUID to);
    List<UserDto> getSubscribers(UUID id);
    List<UserDto> getSubscriptions(UUID id);
}
