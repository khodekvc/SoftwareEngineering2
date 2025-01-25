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
public class SignUpPetOwnerServlet extends HttpServlet {

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
        HttpSession session = request.getSession();

        if ("next".equals(action)) {
            session.setAttribute("fname", request.getParameter("fname"));
            session.setAttribute("lname", request.getParameter("lname"));
            session.setAttribute("email", request.getParameter("email"));
            session.setAttribute("contact", request.getParameter("contact"));
            session.setAttribute("address", request.getParameter("address"));
            session.setAttribute("password", request.getParameter("password"));
            session.setAttribute("confirmpassword", request.getParameter("confirmpassword"));
            
            response.sendRedirect("signup-petinfo.jsp");
        } else if ("signup".equals(action)) {
            String fname = (String) session.getAttribute("fname");
            String lname = (String) session.getAttribute("lname");
            String email = (String) session.getAttribute("email");
            String contactNum = (String) session.getAttribute("contact");
            String address = (String) session.getAttribute("address");
            String userPassword = (String) session.getAttribute("password");
            String confirmPassword = (String) session.getAttribute("confirmpassword");
            
            String petName = request.getParameter("petname");
            String petGender = request.getParameter("gender");
            String petSpecies = request.getParameter("species");
            String petBreed = request.getParameter("breed");
            String petBirthday = request.getParameter("birthdate");
            boolean petVitality = true;
            boolean petStatus = true;
            
            if (!userPassword.equals(confirmPassword)) {
                response.getWriter().write("Passwords do not match.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            String encryptionSecretKey = getServletContext().getInitParameter("encryptionKey");
            String encryptionCipher = getServletContext().getInitParameter("encryptionCipher");
            String encryptedPassword = PasswordUtility.encryptPassword(userPassword, encryptionSecretKey, encryptionCipher);
            
            if (encryptedPassword == null) {
                response.getWriter().write("Error encrypting password.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {

                    try {
                        String query = "INSERT INTO users (user_firstname, user_lastname, user_email, user_contact, user_password, user_role) VALUES (?, ?, ?, ?, ?, 'owner')";
                        int userId = -1;

                        try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                            pstmt.setString(1, fname);
                            pstmt.setString(2, lname);
                            pstmt.setString(3, email);
                            pstmt.setString(4, contactNum);
                            pstmt.setString(5, encryptedPassword);

                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                ResultSet rs = pstmt.getGeneratedKeys();
                                if (rs.next()) {
                                    userId = rs.getInt(1);
                                }
                            } else {
                                throw new SQLException("Failed to insert user data.");
                            }
                        }
                        
                        query = "INSERT INTO owner (user_id, owner_address) VALUES (?, ?)";
                        try (PreparedStatement pstmt2 = conn.prepareStatement(query)) {
                            pstmt2.setInt(1, userId);
                            pstmt2.setString(2, address);
                            pstmt2.executeUpdate();
                        }
                        
                        query = "INSERT INTO pet_info (user_id, pet_name, pet_species, pet_breed, pet_birthday, pet_gender, pet_vitality, pet_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmt3 = conn.prepareStatement(query)) {
                            pstmt3.setInt(1, userId);
                            pstmt3.setString(2, petName);
                            pstmt3.setString(3, petSpecies);
                            pstmt3.setString(4, petBreed != null && !petBreed.isEmpty() ? petBreed : null);
                            pstmt3.setString(5, petBirthday != null && !petBirthday.isEmpty() ? petBirthday : null);
                            pstmt3.setString(6, petGender);
                            pstmt3.setBoolean(7, petVitality);
                            pstmt3.setBoolean(8, petStatus);

                            pstmt3.executeUpdate();
                        }
                        
                        
                        response.sendRedirect("login.jsp");
                    } catch (SQLException ex) {
                        conn.rollback();
                        throw ex;
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }
}
