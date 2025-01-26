package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Shei
 */
public class EmailUtility {
    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        String from = "sheigllrd24926@gmail.com";
        String host = "smtp.gmail.com";
        String username = "sheigllrd24926@gmail.com";
        String password = "gdvn kclx khwy wdfr";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
