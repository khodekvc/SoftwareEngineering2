package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import util.PasswordUtility;
import util.CaptchaUtility;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Shei
 */
public class LoginServlet extends HttpServlet {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int captchaLength;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbUrl = getServletContext().getInitParameter("dbUrl");
        dbUsername = getServletContext().getInitParameter("dbUsername");
        dbPassword = getServletContext().getInitParameter("dbPassword");
        captchaLength = Integer.parseInt(getServletContext().getInitParameter("captchaLength"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CaptchaUtility captchaUtility = new CaptchaUtility();
        String captcha = captchaUtility.generateCaptcha(captchaLength);
        
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captcha);
        
        BufferedImage captchaImage = captchaUtility.generateCaptchaImage(captcha);
        
        response.setContentType("image/jpeg");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(captchaImage, "jpg", baos);
            baos.flush();
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userCaptcha = request.getParameter("captcha");
        HttpSession session = request.getSession();
        
        CaptchaUtility captchaUtility = new CaptchaUtility();
        if (!captchaUtility.isCaptchaCorrect(session, userCaptcha)) {
            request.setAttribute("errorMessage", "Incorrect CAPTCHA. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                String query = "SELECT user_id, user_firstname, user_lastname, user_role, user_password FROM users WHERE user_email = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, email);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String encryptedPassword = rs.getString("user_password");
                            String firstName = rs.getString("user_firstname");
                            String lastName = rs.getString("user_lastname");
                            String role = rs.getString("user_role");
                            
                            String encryptionSecretKey = getServletContext().getInitParameter("encryptionKey");
                            String encryptionCipher = getServletContext().getInitParameter("encryptionCipher");
                            String decryptedPassword = PasswordUtility.decryptPassword(encryptedPassword, encryptionSecretKey, encryptionCipher);
                            
                            if (decryptedPassword != null && decryptedPassword.equals(password)) {
                                int userId = rs.getInt("user_id");
                                session.setAttribute("userId", userId);
                                session.setAttribute("firstName", firstName);
                                session.setAttribute("lastName", lastName);
                                session.setAttribute("role", role);
                                
                                if ("owner".equalsIgnoreCase(role)) {
                                    response.sendRedirect("landing.jsp");
                                } else if ("doctor".equalsIgnoreCase(role)) {
                                    response.sendRedirect("doctor-dashboard.jsp");
                                } else if ("clinician".equalsIgnoreCase(role)) {
                                    response.sendRedirect("clinician-dashboard.jsp");
                                } else {
                                    response.sendRedirect("dashboard.jsp");
                                }
                            } else {
                                request.setAttribute("errorMessage", "Invalid email or password.");
                                request.getRequestDispatcher("login.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("errorMessage", "Invalid email or password.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "An error occurred. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}