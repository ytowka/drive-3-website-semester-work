package org.danilkha.controllers.registration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.danilkha.ContentTypes;
import org.danilkha.Result;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.CodeGenerator;
import org.danilkha.utils.FileProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@MultipartConfig(
        fileSizeThreshold = 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
@WebServlet(name = "registrationApi", value = "/register")
public class RegistrationApiServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute("AUTH_SERVICE");
        req.setCharacterEncoding("UTF-8");
        try {
            resp.setContentType(ContentTypes.JSON);
            ObjectMapper objectMapper = new ObjectMapper();

           /* Result<UserDto> regResult = authenticationService.registerUser("", "", "", "");

            if(regResult instanceof Result.Success<UserDto> result){
                resp.setStatus(200);
            }else if(regResult instanceof Result.Error<UserDto> result){
                resp.setStatus(409);

            }*/
            FileProvider fileProvider = new FileProvider(getServletContext().getRealPath("uploaded/"), new CodeGenerator());

            for (Part part : req.getParts()) {
                if(part.getName().equals("picture")){
                    String name = fileProvider.saveFile(part.getInputStream(), part.getSubmittedFileName());
                    System.out.println("saved file: "+name);
                }
                System.out.println("part name: "+part.getName());
            }
            for (Map.Entry<String, String[]> stringEntry : req.getParameterMap().entrySet()) {
                System.out.println(stringEntry.getKey() + ": "+ Arrays.toString(stringEntry.getValue()));
            }
            RegistrationResponse response = new RegistrationResponse("OK", req.getParameter("username"));
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
