package net.thumbtack.school.threads;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Message {
    private String emailAddress;
    private String sender;
    private String subject;
    private String body;

    public Message(String emailAddress) {
        this.emailAddress = emailAddress;
        this.sender = "inbox@gmail.com";
        this.subject = "hello world!";
        this.body = "Say hi in response!";
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

class Transport {
    // SMTP сервер, используемый для отправки
    private String host;
    private int port;

    public Transport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void send(Message message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        // Если почтовый сервер использует SSL
        props.put("mail.smtp.ssl.enable", "true");
        // Указываем порт SMTP сервера.
        props.put("mail.smtp.port", port);
        // Большинство SMTP серверов, используют авторизацию.
        props.put("mail.smtp.auth", "true");
        // Включение debug-режима
        props.put("mail.debug", "true");
        // Авторизируемся.
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            // Указываем логин пароль, от почты, с которой будем отправлять сообщение.
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("login", "password");
            }
        });
        try {
            // Создание объекта сообщения
            MimeMessage msg = new MimeMessage(session);

            // Установка атрибутов сообщения
            msg.setFrom(new InternetAddress(message.getSender()));
            InternetAddress[] address = {new InternetAddress(message.getEmailAddress())};
            msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
            msg.setSubject(message.getSubject());
            msg.setSentDate(new Date());

            // Установка тела сообщения
            msg.setText(message.getBody());

            // Отправка сообщения
            javax.mail.Transport.send(msg);
        }
        catch (MessagingException mex) {
            // Печать информации об исключении в случае его возникновения
            mex.printStackTrace();
        }
        System.out.println("message " + Thread.currentThread().getId());
    }
}

public class Task14 {
    public static void main(String[] args) throws IOException {

        File file = new File("addresses.txt");
        List<String> addresses = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                 addresses.add(line);
        }
        System.out.println("read is done");

        ExecutorService es = Executors.newFixedThreadPool(4);
        Iterator<String> i = addresses.iterator();
        while (i.hasNext()) {
            String address = i.next();
            Message message = new Message(address);
            es.execute(new Thread(() -> {
                try {
                    new Transport("smtp.yourisp.net", 25).send(message);
                    //System.out.println("message" + Thread.currentThread().getId());
                } catch (Exception e) {
                    System.out.println(e.toString() + Thread.currentThread().getId());
                }
            }));
        }
        i.remove();
        es.shutdown();

    }
}
