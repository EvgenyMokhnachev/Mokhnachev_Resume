package services;

import em.server.HTMLViewer;
import entities.ContactMe;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String LOGIN = "MokhnachevResume@gmail.com";

    private static final String PASSWORD = "GetRandom911Pass45694";

    private static final String SERVER = "smtp.gmail.com";

    private static final String PORT = "465";

    private static final String SSL_PORT = "465";

    private static final String HOST_NAME= "www.mokhnachev.info";

    private static final String SITE_NAME = "Mokhnachev Resume";

    private static final String MAIL_FROM = "MokhnachevResume@gmail.com";

    private static final Session session;

    static {
        Properties props = new Properties();
        props.put("mail.smtp.host", SERVER);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);

        session = Session.getDefaultInstance(
                props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(LOGIN, PASSWORD);
                    }
                }
        );
    }

    private static void sendWithNewThread(String email, String subject, String emailFrom, String content) throws Exception {
        new Thread(() -> {
            try {
                send(email, subject, emailFrom, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private static void send(String email, String subject, String emailFrom, String content) throws Exception {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendContactMeMessage(ContactMe contactMe) {
        String subject = String.format("New message to Mokhnachev.Info");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", contactMe.name);
            params.put("email", contactMe.email);
            params.put("phone", contactMe.phone);
            params.put("message", contactMe.message);
            String parsedHtml = HTMLViewer.parse("webapp/emails/commentMe.html", params).getView();
            send("johnmohnachev@gmail.com", subject, MAIL_FROM, parsedHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}