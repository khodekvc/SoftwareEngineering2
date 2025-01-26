package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.servlet.http.HttpSession;

public class CaptchaUtility {

    private int captchaLength = 6;
    private int imageWidth = 150;
    private int imageHeight = 40;
    
    public String generateCaptcha(int n) {
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captcha = new StringBuilder();
        Random rand = new Random();
        while (n-- > 0) {
            int index = rand.nextInt(chrs.length());
            captcha.append(chrs.charAt(index));
        }
        return captcha.toString();
    }
    
    public BufferedImage generateCaptchaImage(String captcha) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageHeight);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (imageWidth - fm.stringWidth(captcha)) / 2;
        int y = (fm.getAscent() + (imageHeight - (fm.getAscent() + fm.getDescent())) / 2);
        g2d.drawString(captcha, x, y);

        g2d.dispose();
        return image;
    }
    
    public boolean isCaptchaCorrect(HttpSession session, String userCaptcha) {
        String storedCaptcha = (String) session.getAttribute("captcha");
        return storedCaptcha != null && storedCaptcha.equals(userCaptcha);
    }
}
