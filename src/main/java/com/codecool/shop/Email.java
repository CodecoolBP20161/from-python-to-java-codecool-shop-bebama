package com.codecool.shop;

public class Email {

    private static Integer port = 60009;
    private String sender;
    private String recipient;
    private String subject;
    private String text;
    private String recipientName;

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer port) {
        Email.port = port;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Email(String sender, String recipient, String subject, String text, String recipientName){
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
        this.recipientName = recipientName;
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
}
