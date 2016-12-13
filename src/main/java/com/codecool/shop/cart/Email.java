package com.codecool.shop.cart;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    private String sender;
    private String recipient;
    private String subject;
    private String text;
    private String recipientName;
    private String userName;

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Email(String sender, String recipient, String subject, String text, String recipientName, String userName){
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
        this.recipientName = recipientName;
        this.userName = userName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private void formatText(){
        this.text = "Dear " + this.recipientName + "!"
                + "\n \n" + this.text + this.userName + "\n \n"
                + "Happy browsing and shopping :)";
    }

    public void emailSender(){
        // Get system properties
        Properties properties = System.getProperties();

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
//            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Final email form
            formatText();

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Email email = new Email(
                "bebamashop@gmail.com",
                "makaimark@gmail.com",
                "Confirmation email about your registration",
                "Now you are registered to our fantastic webshop, BeBaMa.\nYour username is: ",
                "Mark",
                "márki_márk");
        email.emailSender();
    }
}
