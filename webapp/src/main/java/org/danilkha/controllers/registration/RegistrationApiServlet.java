package org.danilkha.controllers.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.danilkha.ValidationConfig;
import org.danilkha.ContentTypes;
import org.danilkha.Result;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.MultipartUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

@MultipartConfig(
        fileSizeThreshold = 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
@WebServlet(name = "registrationApi", value = "/register")
public class RegistrationApiServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);
        req.setCharacterEncoding("UTF-8");
        try {
            resp.setContentType(ContentTypes.JSON);
            ObjectMapper objectMapper = new ObjectMapper();

            if(!isFieldsValid(req)){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                RegistrationResponse response = new RegistrationResponse("ERROR","invalid_fields");
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
                RegistrationResponse response = new RegistrationResponse("OK", "");
                resp.getWriter().write(objectMapper.writeValueAsString(response));
            }else if(regResult instanceof Result.Error<UserDto> result){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                RegistrationResponse response = new RegistrationResponse("ERROR",result.getMessage());
                resp.getWriter().write(objectMapper.writeValueAsString(response));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isFieldsValid(HttpServletRequest req){
        Pattern usernameRegex = Pattern.compile(RegistrationServlet.usernameRegexPattern);

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
