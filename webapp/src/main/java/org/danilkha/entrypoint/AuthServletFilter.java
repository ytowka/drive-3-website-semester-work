package org.danilkha.entrypoint;

import org.danilkha.Result;
import org.danilkha.dto.UserDto;
import org.danilkha.services.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServletFilter extends HttpFilter {

    public static final String USER_ATTRIBUTE = "user";
    public static final String COOKIE_CREDENTIALS = "credentials";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);
        HttpSession session = req.getSession(true);
        UserDto currentUser = (UserDto) session.getAttribute(USER_ATTRIBUTE);
        if(currentUser != null){
            chain.doFilter(req, res);
            return;
        }
        for(Cookie cookie : req.getCookies()){
            if(cookie.getName().equals(COOKIE_CREDENTIALS)){
                Result<UserDto> result = authenticationService.fastAuth(cookie.getValue());
                if(result instanceof Result.Success<UserDto> success){
                    currentUser = ((Result.Success<UserDto>) result).getData();
                    session.setAttribute(USER_ATTRIBUTE, success.getData());
                    break;
                }else{
                }

            }
        }
        chain.doFilter(req, res);
    }
}
