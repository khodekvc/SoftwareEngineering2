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
public class SignUpEmployeeServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        String userCaptcha = request.getParameter("captcha");
        
        if ("signup".equals(action)) {
            signup(request, response, userCaptcha);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response, String userCaptcha) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        
        HttpSession session = request.getSession();
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("signup-employees.jsp").forward(request, response);
            return;
        }
        
        String encryptionSecretKey = getServletContext().getInitParameter("encryptionKey");
        String encryptionCipher = getServletContext().getInitParameter("encryptionCipher");
        String encryptedPassword = PasswordUtility.encryptPassword(password, encryptionSecretKey, encryptionCipher);
            
        if (encryptedPassword == null) {
            response.getWriter().write("Error encrypting password.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        CaptchaUtility captchaUtility = new CaptchaUtility();
            if (!captchaUtility.isCaptchaCorrect(session, userCaptcha)) {
                request.setAttribute("errorMessage", "Incorrect CAPTCHA. Please try again.");
                request.getRequestDispatcher("signup-employees.jsp").forward(request, response);
                return;
            }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {

                String query = "INSERT INTO users (user_firstname, user_lastname, user_email, user_contact, user_password, user_role) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, fname);
                    pstmt.setString(2, lname);
                    pstmt.setString(3, email);
                    pstmt.setString(4, contact);
                    pstmt.setString(5, encryptedPassword);
                    pstmt.setString(6, role);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        response.sendRedirect("signup-accesscode.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Sign up failed. Please try again.");
                        request.getRequestDispatcher("signup-employees.jsp").forward(request, response);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("signup-employees.jsp").forward(request, response);
        }
    }
}
