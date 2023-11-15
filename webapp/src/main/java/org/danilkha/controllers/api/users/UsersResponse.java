package org.danilkha.controllers.api.users;

import java.util.List;

public record UsersResponse(
        List<UserResponse> users
) {

}
