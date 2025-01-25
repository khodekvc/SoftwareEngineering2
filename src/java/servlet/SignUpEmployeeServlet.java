package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import util.PasswordUtility;

/**
 *
 * @author Shei
 */
public class SignUpEmployeeServlet extends HttpServlet {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbUrl = config.getInitParameter("dbUrl");
        dbUsername = config.getInitParameter("dbUsername");
        dbPassword = config.getInitParameter("dbPassword");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("signup".equals(action)) {
            signup(request, response);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        
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
                        response.sendRedirect("login.jsp");
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
