package org.danilkha.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {


    // https://app.brevo.com/
    // https://mail.rambler.ru/
    // Donil0304


    // Sender's email ID and password
    String senderEmail = "cars.itis@rambler.ru";
    String password = "TjRKIApnfCtkqxBa";

    private Session session;
    public EmailSender(){

        // Set properties and authentication info for the email client

        // Authenticator object to provide authentication for the email server
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        };


        // Sender's SMTP server details
        String host = "smtp-relay.brevo.com";
        int port = 465;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user","danil");
        properties.put("mail.smtp.ssl.enable", true);

        // Create a Session instance with the properties and authentication
        session = Session.getInstance(properties, auth);
    }

    public void sendEmail(String recipientAddress, String message, String topic) {
        try {
            // Create a MimeMessage object
            MimeMessage mimeMessage = new MimeMessage(session);

            // Set the sender address
            mimeMessage.setFrom(new InternetAddress(senderEmail));

            // Set the recipient address
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));

            // Set the email subject
            mimeMessage.setSubject(topic);

            // Set the email content
            mimeMessage.setText(message);
            System.out.println("sending message");

            // Send the email
            Transport.send(mimeMessage);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}