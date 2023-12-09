package l; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegisterServlet") // Ensure this matches the URL pattern you will use
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password"); 
        // Use try-with-resources to ensure closure of resources
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userpass", "root", "Example@2022#");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO login (uname, password) VALUES (?, ?)")) {

            ps.setString(1, username);
            ps.setString(2, password);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("login.jsp"); // Redirect to login page after successful registration
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log to server logs, consider a logging framework
            request.setAttribute("errorMessage", "A database error occurred. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
