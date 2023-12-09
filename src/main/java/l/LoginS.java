package l;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.jasper.tagplugins.jstl.core.Out;

/**
 * Servlet implementation class LoginS
 */
public class LoginS extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); // Set the content type for the response
        PrintWriter out = response.getWriter(); // Use PrintWriter to write out to the response

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userpass", "root", "Example@2022#");
            String n = request.getParameter("txtName");
            String p = request.getParameter("txtPwd");
            PreparedStatement ps = con.prepareStatement("select uname from login where uname =? and password=?");
            ps.setString(1, n);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RequestDispatcher rd = request.getRequestDispatcher("Welcome.jsp");
                rd.forward(request, response);
            } else {
                out.println("<font color='blue' size='15'>Login Failed!</font><br>"); 
                out.println("<a href='login.jsp'>Back!</a>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            out.close(); 
        }
    }
}
