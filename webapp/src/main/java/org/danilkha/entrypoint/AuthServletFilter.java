package org.danilkha.entrypoint;

import org.danilkha.Result;
import org.danilkha.dto.UserDto;
import org.danilkha.services.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServletFilter extends HttpFilter {

    public static final String USER_ATTRIBUTE = "user";
    public static final String COOKIE_CREDENTIALS = "credentials";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);
        UserDto currentUser = (UserDto) req.getSession().getAttribute(USER_ATTRIBUTE);
        if(currentUser != null){
            System.out.println("current user not null");
            chain.doFilter(req, res);
            return;
        }
        for(Cookie cookie : req.getCookies()){
            if(cookie.getName().equals(COOKIE_CREDENTIALS)){
                System.out.println("cookies user not null");
                Result<UserDto> result = authenticationService.fastAuth(cookie.getValue());
                if(result instanceof Result.Success<UserDto> success){
                    System.out.println("success auth");
                    req.getSession().setAttribute(USER_ATTRIBUTE, success.getData());
                    break;
                }else{
                    System.out.println("bad auth");
                }

            }
        }
        chain.doFilter(req, res);
    }
}
