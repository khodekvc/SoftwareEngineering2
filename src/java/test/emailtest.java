package test;

import javax.mail.MessagingException;
import util.EmailUtility;

/**
 * Test class for EmailUtility.
 *
 * @author Test
 */
public class emailtest {
    public static void main(String[] args) {
        String recipient = "gallardo.sheimariz@gmail.com"; // Replace with a valid email address
        String subject = "email kho";
        String body = "mic test mic test.";

        try {
            EmailUtility.sendEmail(recipient, subject, body);
            System.out.println("Email sent successfully to " + recipient);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
