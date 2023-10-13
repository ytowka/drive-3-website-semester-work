package org.danilkha;

import org.danilkha.dto.UserDto;
import org.danilkha.repos.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

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
    public static void main(String[] args) {
        Connection connection = getConnection();

        UserRepository userRepository = new UserRepositoryImpl(connection);

        /*try {
            userRepository.RegisterUser(
                    new UserDto(0, "danil", "donil.0304@yandex.ru"),
                    "qwerty"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        Scanner in = new Scanner(System.in);

        while (true){
            System.out.println("email");
            String email = in.nextLine();
            System.out.println("password");
            String password = in.nextLine();

            try {
                boolean success = userRepository.authUser(email, password);

                System.out.println("success: "+success);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
