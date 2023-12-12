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
import java.sql.ResultSet;

@WebServlet("/SearchFlightsServlet")
public class FlightSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");
        String date = request.getParameter("date");
        String tripType = request.getParameter("tripType");
        boolean flexibleDates = request.getParameter("flexibleDates") != null;

        // Logic to connect to the database and search for flights
        // Implement your SQL query using the parameters above
        // Forward to a JSP page to display the results
    }
}