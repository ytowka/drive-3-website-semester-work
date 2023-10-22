package org.danilkha;

import org.danilkha.dao.ConfirmationCodesDao;
import org.danilkha.dao.PasswordResetCodesDao;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.repo.AuthenticationRepositoryImpl;
import org.danilkha.repos.AuthenticationRepository;
import org.danilkha.utils.CodeGenerator;
import org.danilkha.utils.EmailSender;
import org.danilkha.utils.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static final String HOST = "jdbc:postgresql://localhost:5432/car_configurator_database";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        Connection connection = DriverManager.getConnection(HOST, USER, PASS);

        ConnectionProvider connectionProvider = () -> connection;


        ConfirmationCodesDao confirmationCodesDao = new ConfirmationCodesDao.Impl(connectionProvider);
        PasswordResetCodesDao passwordResetCodesDao = new PasswordResetCodesDao.Impl(connectionProvider);
        UserDao userDao = new UserDao.Impl(connectionProvider);
        PasswordEncoder passwordEncoder = new PasswordEncoder(MessageDigest.getInstance("SHA-256"));
        CodeGenerator codeGenerator = new CodeGenerator();
        EmailSender emailSender = new EmailSender();

        AuthenticationRepository authenticationRepository = new AuthenticationRepositoryImpl(
                userDao, confirmationCodesDao, passwordResetCodesDao, passwordEncoder, 1, 1, codeGenerator, emailSender
        );

        Scanner in = new Scanner(System.in);

        while (true){
            String command = in.nextLine();
            switch (command){
                case "login" ->{
                    System.out.println("email: ");
                    String email = in.nextLine();
                    System.out.println("password: ");
                    String password = in.nextLine();
                    UserDto userDto = authenticationRepository.authUser(email, password);
                    System.out.println(userDto);
                }
                case "reg" ->{
                    System.out.println("name: ");
                    String name = in.nextLine();
                    System.out.println("email: ");
                    String email = in.nextLine();
                    System.out.println("avatarUri: ");
                    String avatarUri = in.nextLine();
                    System.out.println("password: ");
                    String password = in.nextLine();
                    UserDto userDto = authenticationRepository.registerUser(name, email, avatarUri, password);
                    System.out.println(userDto);
                }
                case "confEmail" ->{
                    System.out.println("uuid: ");
                    String uuid = in.nextLine();
                    System.out.println("code: ");
                    String code = in.nextLine();
                    boolean confirmEmail = authenticationRepository.confirmEmail(UUID.fromString(uuid), Integer.parseInt(code));
                    System.out.println(confirmEmail);
                }
                case "reqConfEmail" ->{
                    System.out.println("uuid: ");
                    String uuid = in.nextLine();
                    authenticationRepository.sendEmailConfirmationCode(UUID.fromString(uuid));
                }
                case "reqResetPassword" ->{
                    System.out.println("email: ");
                    String email = in.nextLine();
                    boolean result = authenticationRepository.requestPasswordReset(email);
                    System.out.println(result);
                }
                case "resetPassword" ->{
                    System.out.println("link: ");
                    String link = in.nextLine();
                    System.out.println("new password: ");
                    String password = in.nextLine();
                    boolean result = authenticationRepository.resetPassword(link, password);
                    System.out.println(result);
                }
                default -> {
                    System.out.println(command);
                }
            }
            System.out.println();
        }
    }
}
