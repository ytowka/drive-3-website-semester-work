package org.danilkha.utils;

import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.services.AuthenticationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RememberMeCookies {

    public static void addCredentialsCookie(HttpServletResponse response, UserDto userDto){
        Cookie cookie = new Cookie(AuthServletFilter.COOKIE_CREDENTIALS, userDto.username()+ AuthenticationService.IDENTIFIER_SPLITTER+userDto.passwordHash());
        response.addCookie(cookie);
    }
}
