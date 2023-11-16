package org.danilkha.services;

import org.danilkha.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID id);
}
