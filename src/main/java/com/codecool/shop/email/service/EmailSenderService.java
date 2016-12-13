package com.codecool.shop.email.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.*;
import java.util.*;

/**
 * Created by makaimark on 2016.12.13..
 */
public class EmailSenderService {

    private static EmailSenderService INSTANCE;

    public static EmailSenderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailSenderService();
        }
        return INSTANCE;
    }

    private static String formatWelcomeEmail(String name) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        return String.format("You are receiving this email, because you have successfully registered at %s to our webshop, " +
                "the BeBaMa Codecool Shop with the username '%s'", dateFormat.format(date), name);
    }

    private String formatText(String recipientName, String body) {
        String text = "Dear " + recipientName + "!"
                + "\n \n" + body + "\n \n"
                + "Happy browsing and shopping :)" + "\n \n" + "The BeBaMa Team from Codecool Shop";
        return text;
    }

    public void emailSender(String sender, String recipient, String subject, String recipientName) {

        // Setup mail server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("bebamashop@gmail.com", "bebama2016");
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Final email form and the actual message
            message.setText(formatText(recipientName, formatWelcomeEmail(recipientName)));

            // Send message
            Transport.send(message);

            System.out.println("Message sent successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
