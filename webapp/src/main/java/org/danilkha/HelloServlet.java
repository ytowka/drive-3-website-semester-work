package org.danilkha;

import org.danilkha.repos.UserRepository;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "helloServlet", value = "/hello", asyncSupported = true)
public class HelloServlet extends HttpServlet {
    private String message;

    private static final String HOST = "jdbc:postgresql://localhost:5432/car_configurator_database";
    private static final String USER = "postgres";
    private static final String PASS = "admin";


    private static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(HOST, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //private UserRepository repository= new UserRepositoryImpl(getConnection());

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AsyncContext asyncContext = req.startAsync(req, resp);

        new Thread(() ->{

            ServletResponse response = asyncContext.getResponse();
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println("<html><body>");
            out.println("<h1>" + message +this.hashCode() + "</h1>");
            out.println("</body></html>");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            asyncContext.complete();
        }).start();

        // Hello

    }

    public void destroy() {
    }
}