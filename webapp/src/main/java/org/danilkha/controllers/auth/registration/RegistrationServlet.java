package org.danilkha.controllers.auth.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.ContentTypes;
import org.danilkha.Result;
import org.danilkha.ValidationConfig;
import org.danilkha.controllers.BaseResponse;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.MultipartUtils;
import org.danilkha.utils.PropertyReader;
import org.danilkha.utils.RememberMeCookies;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

@MultipartConfig(
        fileSizeThreshold = 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
@WebServlet(name = "sign-up", value = "/sign-up")
public class RegistrationServlet extends HtmlServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE) != null){
            resp.sendRedirect("feed");
            resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        }else{
            super.doGet(req, resp);
        }
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        root.put("usernameRegexPattern", ValidationConfig.usernameRegexPattern);
        root.put("emailRegexPattern", ValidationConfig.emailRegexPattern);
        root.put("minNameLength", ValidationConfig.minNameLength);
        root.put("maxNameLength",  ValidationConfig.maxNameLength);
        root.put("minPasswordLength", ValidationConfig.mimPasswordLength);
        return freemarkerCfg.getTemplate("auth/sign-up.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);
        req.setCharacterEncoding("UTF-8");
        try {
            resp.setContentType(ContentTypes.JSON);
            ObjectMapper objectMapper = new ObjectMapper();

            if(!isFieldsValid(req)){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                BaseResponse response = new BaseResponse("ERROR","invalid_fields");
                resp.getWriter().write(objectMapper.writeValueAsString(response));
            }


            Part part = req.getPart("picture");
            InputStream picture = null;
            String fileName = null;
            if(part != null){
                picture = part.getInputStream();
                fileName = MultipartUtils.getFileName(part);
            }
            Result<UserDto> regResult = authenticationService.registerUser(
                    picture,
                    fileName,
                    req.getParameter("username"),
                    req.getParameter("firstname"),
                    req.getParameter("surname"),
                    req.getParameter("email"),
                    req.getParameter("password")
            );

            if(regResult instanceof Result.Success<UserDto> result){
                resp.setStatus(HttpServletResponse.SC_OK);
                if("on".equals(req.getParameter("rememberMe"))){
                    RememberMeCookies.addCredentialsCookie(resp, result.getData());
                }
                req.getServletContext().setAttribute(AuthServletFilter.USER_ATTRIBUTE, result.getData());
                BaseResponse response = new BaseResponse("OK", "");
                resp.getWriter().write(objectMapper.writeValueAsString(response));
            }else if(regResult instanceof Result.Error<UserDto> result){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                BaseResponse response = new BaseResponse("ERROR",result.getMessage());
                resp.getWriter().write(objectMapper.writeValueAsString(response));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isFieldsValid(HttpServletRequest req){
        Pattern usernameRegex = Pattern.compile(ValidationConfig.usernameRegexPattern);

        String username = req.getParameter("username");
        String firstname = req.getParameter("firstname");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(!isNameInBounds(username)){
            return false;
        }
        if(!usernameRegex.matcher(username).matches()){
            return false;
        }
        if (!isNameInBounds(firstname)){
            return false;
        }

        if(!isNameInBounds(surname)){
            return false;
        }
        Pattern emailRegex = Pattern.compile(ValidationConfig.emailRegexPattern);
        if(!emailRegex.matcher(email).matches()){
            return false;
        }
        if(password.length() < ValidationConfig.mimPasswordLength){
            return false;
        }
        return true;
    }

    private static boolean isNameInBounds(String s){
        return s.length() >= ValidationConfig.minNameLength && s.length() <= ValidationConfig.maxNameLength;
    }
}
